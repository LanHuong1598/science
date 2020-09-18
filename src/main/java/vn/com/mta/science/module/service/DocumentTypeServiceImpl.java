package vn.com.mta.science.module.service;

import com.sun.tools.doclets.formats.html.markup.DocType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.repository.service.detail.impl.AuditableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.DocumentType;
import vn.com.mta.science.module.schema.AffiliationGet;
import vn.com.mta.science.module.schema.DocumentTypeGet;
import vn.com.mta.science.module.service.db.AffiliationDAO;
import vn.com.mta.science.module.service.db.DocumentTypeDAO;

@SuppressWarnings("Duplicates")
@Service("documentTypeService")
public class DocumentTypeServiceImpl extends AuditableGeneratedIDSchemaServiceImpl<DocumentTypeGet, DocumentType> implements DocumentTypeService {

    @Autowired
    private DocumentTypeDAO documentTypeDAO;

    @Override
    public DocumentTypeDAO getDAO() {
        return documentTypeDAO;
    }

    @Override
    public DocumentTypeGet convert(DocumentType affiliation) {
        return new DocumentTypeGet(affiliation);
    }
}
