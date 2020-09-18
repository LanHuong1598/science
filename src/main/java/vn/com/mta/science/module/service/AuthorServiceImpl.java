package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Author;
import vn.com.mta.science.module.model.GroupMember;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.db.AuthorDAO;
import vn.com.mta.science.module.service.db.GroupMemberDAO;
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

    @Override
    public AuthorDAO getDAO() {
        return authorDAO;
    }

    @Override
    public AuthorGet convert(Author author) {
        AuthorGet authorGet = new AuthorGet(author);

        GroupMemberFilter filter = new GroupMemberFilter();
        filter.setAuthorId(author.getId());
        List<GroupMember> ids =  groupMemberDAO.getPageOfData(filter, null);
        if (ids != null) authorGet.setGroupIds(ids.stream().map(GroupMember::getGroupId).collect(Collectors.toList()));
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

        return convert(author);
    }
}
