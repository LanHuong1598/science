package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;
import vn.com.itechcorp.base.util.Pair;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Author;
import vn.com.mta.science.module.model.Email;
import vn.com.mta.science.module.model.GroupMember;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.db.AffiliationDAO;
import vn.com.mta.science.module.service.db.AuthorDAO;
import vn.com.mta.science.module.service.db.EmailDAO;
import vn.com.mta.science.module.service.db.GroupMemberDAO;
import vn.com.mta.science.module.service.filter.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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


    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public AuthorGet convert(Author author) {
        AuthorGet authorGet = new AuthorGet(author);

        if (author.getBirthdate() != null)
            authorGet.setBirthdate(dateFormat.format(author.getBirthdate()));

        Affiliation dv = affiliationDAO.getById(author.getAffiliationId());
        if (dv != null) {
            String donvi = dv.getName();
            if (dv.getParentId() != null) {
                dv = affiliationDAO.getById(dv.getParentId());
                if (dv != null) {
                    donvi = donvi + " - " + dv.getName();
                    if (dv.getParentId() != null) {
                        dv = affiliationDAO.getById(dv.getParentId());
                        if (dv != null) donvi = donvi + " - " + dv.getName();
                    }
                }
            }

            authorGet.setDonvi(donvi);

        }
        GroupMemberFilter filter = new GroupMemberFilter();
        filter.setAuthorId(author.getId());
        List<GroupMember> ids = groupMemberDAO.getPageOfData(filter, null);
        if (ids != null) authorGet.setGroupIds(ids.stream().map(GroupMember::getGroupId).collect(Collectors.toList()));

        EmailAuthorFilter emailAuthorFilter = new EmailAuthorFilter();

        emailAuthorFilter.setAuthorId(author.getId());
        List<Email> d = emailDAO.getPageOfData(emailAuthorFilter, null);
        if (d != null) authorGet.setEmail(d.stream().map(Email::getName).collect(Collectors.toList()));
        return authorGet;
    }

    @Override
    public AuthorGet create(GeneratedIDSchemaCreate<Author> entity, Long callerId) throws APIException {
        AuthorCreate object = (AuthorCreate) entity;

        Author author = authorDAO.create(object.toEntry(), 0L);

        if (object.getGroupIds() != null && !object.getGroupIds().isEmpty())
            for (Long id : object.getGroupIds()) {
                GroupMember groupMember = new GroupMember();
                groupMember.setAuthorId(author.getId());
                groupMember.setGroupId(id);
                groupMemberDAO.create(groupMember, 0L);
            }

        if (object.getEmail() != null && !object.getEmail().isEmpty())
            for (String id : object.getEmail()) {
                Email email = new Email();
                email.setAuthorId(author.getId());
                email.setName(id);
                emailDAO.create(email, 0L);
            }

        return convert(author);
    }

    @Override
    public AuthorGet update(SchemaUpdate<Author, Long> entity, Long callerId) throws APIException {
        AuthorUpdate object = (AuthorUpdate) entity;

        GroupMemberFilter filter = new GroupMemberFilter();
        filter.setAuthorId(object.getId());
        List<GroupMember> groups = groupMemberDAO.getPageOfData(filter, null);
        if (groups != null)
        for (GroupMember groupMember : groups) {
            groupMemberDAO.delete(groupMember, 0L);
        }

        EmailAuthorFilter emailAuthorFilter = new EmailAuthorFilter();

        emailAuthorFilter.setAuthorId(object.getId());
        List<Email> emails = emailDAO.getPageOfData(emailAuthorFilter, null);
        if (emails != null)
        for (Email email : emails) {
            emailDAO.delete(email, 0L);
        }

        if (object.getGroupIds() != null && !object.getGroupIds().isEmpty())
            for (Long id : object.getGroupIds()) {
                GroupMember groupMember = new GroupMember();
                groupMember.setAuthorId(object.getId());
                groupMember.setGroupId(id);
                groupMemberDAO.create(groupMember, 0L);
            }

        if (object.getEmail() != null && !object.getEmail().isEmpty())
            for (String id : object.getEmail()) {
                Email email = new Email();
                email.setAuthorId(object.getId());
                email.setName(id);
                emailDAO.create(email, 0L);
            }

        Author author = authorDAO.getById(object.getId());

        if (!entity.apply(author)) convert(author);
        return convert(getDAO().update(author, callerId));
    }

    @Override
    public Pair<Collection<AuthorGet>, Long> getByAff(AuthorByAffFilter filter) {
        List<AuthorGet> result = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        if (filter.getCap1Id() == null) {
            result = authorDAO.getAll().stream().map(AuthorGet::new).collect(Collectors.toList());
            if (result.size() == 0) return new Pair<>(null, 0L);
            return new Pair<>(result, (long) result.size());
        }

        if (filter.getCap2Id() == null) {
            AffiliationFilter affiliationFilter = new AffiliationFilter();
            affiliationFilter.setParentId(filter.getCap1Id());
            ids = affiliationDAO.getPageOfData(affiliationFilter, null).stream().map(Affiliation::getId).collect(Collectors.toList());
            AuthorFilter authorFilter = new AuthorFilter();
            authorFilter.setIds(ids);
            result = authorDAO.getPageOfData(filter, null).stream().map(AuthorGet::new).collect(Collectors.toList());
            if (result.size() == 0) return new Pair<>(null, 0L);
            return new Pair<>(result, (long) result.size());
        }

        ids.add(filter.getCap2Id());
        AuthorFilter authorFilter = new AuthorFilter();
        authorFilter.setIds(ids);
        result = authorDAO.getPageOfData(authorFilter, null).stream().map(AuthorGet::new).collect(Collectors.toList());
        if (result.size() == 0) return new Pair<>(null, 0L);
        return new Pair<>(result, (long) result.size());
    }
}
