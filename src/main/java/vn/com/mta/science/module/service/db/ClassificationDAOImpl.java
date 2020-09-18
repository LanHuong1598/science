package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.Classification;

@Repository("classificationDAO")
public class ClassificationDAOImpl extends AuditableDAOHbnImpl<Classification, Long> implements ClassificationDAO {

    @Override
    public Class<Classification> getEntityClass() {
        return Classification.class;
    }
}
