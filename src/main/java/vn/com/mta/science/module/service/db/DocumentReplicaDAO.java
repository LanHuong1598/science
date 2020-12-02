package vn.com.mta.science.module.service.db;

import vn.com.itechcorp.base.repository.dao.AuditableDAO;
import vn.com.itechcorp.base.repository.dao.VoidableDAO;
import vn.com.itechcorp.base.repository.service.interfaces.AuditableDbService;
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.model.DocumentReplica;

public interface DocumentReplicaDAO extends AuditableDAO<DocumentReplica, Long> {
}
