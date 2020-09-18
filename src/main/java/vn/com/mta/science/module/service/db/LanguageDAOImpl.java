package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.Language;

@Repository("languageDAO")
public class LanguageDAOImpl extends AuditableDAOHbnImpl<Language, Long> implements LanguageDAO {

    @Override
    public Class<Language> getEntityClass() {
        return Language.class;
    }
}
