package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.DocumentMemberReplica;

@Repository("documentMemberReplicaDAO")
public class DocumentMemberReplicaDAOImpl extends AuditableDAOHbnImpl<DocumentMemberReplica, Long> implements DocumentMemberReplicaDAO {

    @Override
    public Class<DocumentMemberReplica> getEntityClass() {
        return DocumentMemberReplica.class;
    }
}
