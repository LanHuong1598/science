package vn.com.mta.science.module.service.db;

import vn.com.itechcorp.base.repository.dao.VoidableDAO;
import vn.com.mta.science.module.model.Document;

public interface DocumentDAO extends VoidableDAO<Document, Long> {
    Document getByUuid(String uuid);
}
