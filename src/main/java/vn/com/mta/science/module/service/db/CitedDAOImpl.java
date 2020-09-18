package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.Cited;

@Repository("citedDAO")
public class CitedDAOImpl extends AuditableDAOHbnImpl<Cited, Long> implements CitedDAO {

    @Override
    public Class<Cited> getEntityClass() {
        return Cited.class;
    }
}
