package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.DocumentMember;

@Repository("documentMemberDAO")
public class DocumentMemberDAOImpl extends AuditableDAOHbnImpl<DocumentMember, Long> implements DocumentMemberDAO {

    @Override
    public Class<DocumentMember> getEntityClass() {
        return DocumentMember.class;
    }
}
