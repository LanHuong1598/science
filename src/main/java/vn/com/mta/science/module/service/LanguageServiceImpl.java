package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.repository.dao.AuditableDAO;
import vn.com.itechcorp.base.repository.service.detail.impl.AuditableGeneratedIDSchemaServiceImpl;
import vn.com.mta.science.module.model.Language;
import vn.com.mta.science.module.schema.LanguageGet;
import vn.com.mta.science.module.service.db.LanguageDAO;

@SuppressWarnings("Duplicates")
@Service("languageService")
public class LanguageServiceImpl extends AuditableGeneratedIDSchemaServiceImpl<LanguageGet, Language> implements LanguageService {

    @Autowired
    private LanguageDAO languageDAO;

    @Override
    public LanguageGet convert(Language researchGroup) {
        return new LanguageGet(researchGroup);
    }

    @Override
    public AuditableDAO<Language, Long> getDAO() {
        return languageDAO;
    }
}
