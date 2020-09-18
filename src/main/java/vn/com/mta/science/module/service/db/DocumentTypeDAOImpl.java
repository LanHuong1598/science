package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.DocumentType;

@Repository("documentTypeDAO")
public class DocumentTypeDAOImpl extends AuditableDAOHbnImpl<DocumentType, Long> implements DocumentTypeDAO {

    @Override
    public Class<DocumentType> getEntityClass() {
        return DocumentType.class;
    }
}
