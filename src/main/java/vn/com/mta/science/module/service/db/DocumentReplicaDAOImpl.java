package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.model.DocumentReplica;
import vn.com.mta.science.module.schema.DocumentUpdate;

@Repository("documentReplicaDAO")
public class DocumentReplicaDAOImpl extends VoidableDAOHbnImpl<DocumentReplica, Long> implements DocumentReplicaDAO {
    @Override
    public Class<DocumentReplica> getEntityClass() {
        return DocumentReplica.class;
    }
}
