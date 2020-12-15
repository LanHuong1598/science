package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.DocumentReplica;

@Repository("documentReplicaDAO")
public class DocumentReplicaDAOImpl extends AuditableDAOHbnImpl<DocumentReplica, Long> implements DocumentReplicaDAO {
    @Override
    public Class<DocumentReplica> getEntityClass() {
        return DocumentReplica.class;
    }
}
