package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.repository.dao.AuditableDAO;
import vn.com.itechcorp.base.repository.service.detail.impl.AuditableGeneratedIDSchemaServiceImpl;
import vn.com.mta.science.module.model.Cited;
import vn.com.mta.science.module.model.Degree;
import vn.com.mta.science.module.schema.CitedGet;
import vn.com.mta.science.module.schema.DegreeGet;
import vn.com.mta.science.module.service.db.CitedDAO;
import vn.com.mta.science.module.service.db.DegreeDAO;

@SuppressWarnings("Duplicates")
@Service("citedService")
public class CitedServiceImpl extends AuditableGeneratedIDSchemaServiceImpl<CitedGet, Cited> implements CitedService {

    @Autowired
    private CitedDAO degreeDAO;

    @Override
    public CitedGet convert(Cited researchGroup) {
        return new CitedGet(researchGroup);
    }

    @Override
    public AuditableDAO<Cited, Long> getDAO() {
        return degreeDAO;
    }
}
