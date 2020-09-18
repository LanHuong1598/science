package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.mta.science.module.model.Document;

@Repository("documentDAO")
public class DocumentDAOImpl extends VoidableDAOHbnImpl<Document, Long> implements DocumentDAO {
    @Override
    public Class<Document> getEntityClass() {
        return Document.class;
    }
}
