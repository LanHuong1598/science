package vn.com.mta.science.module.service;

import vn.com.itechcorp.base.repository.service.detail.VoidableGeneratedIDSchemaService;
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.schema.DocumentGet;


public interface DocumentService extends VoidableGeneratedIDSchemaService<DocumentGet, Document> {

    public void updatedb();

    DocumentGet getByUuid(String uuid);
}
