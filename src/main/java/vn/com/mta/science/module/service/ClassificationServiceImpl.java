package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.repository.dao.AuditableDAO;
import vn.com.itechcorp.base.repository.service.detail.impl.AuditableGeneratedIDSchemaServiceImpl;
import vn.com.mta.science.module.model.Classification;
import vn.com.mta.science.module.schema.ClassificationGet;
import vn.com.mta.science.module.service.db.ClassificationDAO;

@SuppressWarnings("Duplicates")
@Service("classificationService")
public class ClassificationServiceImpl extends AuditableGeneratedIDSchemaServiceImpl<ClassificationGet, Classification> implements ClassificationService {

    @Autowired
    private ClassificationDAO classificationDAO;

    @Override
    public ClassificationGet convert(Classification researchGroup) {
        return new ClassificationGet(researchGroup);
    }

    @Override
    public AuditableDAO<Classification, Long> getDAO() {
        return classificationDAO;
    }
}
