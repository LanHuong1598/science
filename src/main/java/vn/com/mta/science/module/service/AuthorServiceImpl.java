package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Author;
import vn.com.mta.science.module.model.Email;
import vn.com.mta.science.module.model.GroupMember;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.db.AffiliationDAO;
import vn.com.mta.science.module.service.db.AuthorDAO;
import vn.com.mta.science.module.service.db.EmailDAO;
import vn.com.mta.science.module.service.db.GroupMemberDAO;
import vn.com.mta.science.module.service.filter.EmailAuthorFilter;
import vn.com.mta.science.module.service.filter.GroupMemberFilter;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service("authorService")
public class AuthorServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<AuthorGet, Author> implements AuthorService {

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private GroupMemberDAO groupMemberDAO;

    @Autowired
    private EmailDAO emailDAO;

    @Autowired
    private AffiliationDAO affiliationDAO;

    @Override
    public AuthorDAO getDAO() {
        return authorDAO;
    }

    @Override
    public AuthorGet convert(Author author) {
        AuthorGet authorGet = new AuthorGet(author);

        Affiliation dv = affiliationDAO.getById(author.getAffiliationId());
        String donvi = dv.getName();
        if (dv.getParentId() != null)
        {
            dv = affiliationDAO.getById(dv.getParentId());
            donvi = donvi + " - " + dv.getName();
            if (dv.getParentId() != null)
            {
                dv = affiliationDAO.getById(dv.getParentId());
                donvi = donvi + " - " + dv.getName();
            }
        }

        authorGet.setDonvi(donvi);


        GroupMemberFilter filter = new GroupMemberFilter();
        filter.setAuthorId(author.getId());
        List<GroupMember> ids =  groupMemberDAO.getPageOfData(filter, null);
        if (ids != null) authorGet.setGroupIds(ids.stream().map(GroupMember::getGroupId).collect(Collectors.toList()));

        EmailAuthorFilter emailAuthorFilter = new EmailAuthorFilter();

        emailAuthorFilter.setAuthorId(author.getId());
        List<Email> d =  emailDAO.getPageOfData(filter, null);
        if (ids != null) authorGet.setEmail(d.stream().map(Email::getName).collect(Collectors.toList()));
        return authorGet;
    }

    @Override
    public AuthorGet create(GeneratedIDSchemaCreate<Author> entity, Long callerId) throws APIException {
        AuthorCreate object = (AuthorCreate) entity;

        Author author = authorDAO.create(object.toEntry(), 0L);

        if (object.getGroupIds() != null && !object.getGroupIds().isEmpty())
        for (Long id : object.getGroupIds()){
            GroupMember groupMember = new GroupMember();
            groupMember.setAuthorId(author.getId());
            groupMember.setGroupId(id);
            groupMemberDAO.create(groupMember, 0L);
        }

        if (object.getEmail() != null && !object.getEmail().isEmpty())
            for (String id : object.getEmail()){
                Email email = new Email();
                email.setAuthorId(author.getId());
                email.setName(id);
                emailDAO.create(email, 0L);
            }

        return convert(author);
    }
}
