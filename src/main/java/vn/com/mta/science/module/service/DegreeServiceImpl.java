package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.repository.dao.AuditableDAO;
import vn.com.itechcorp.base.repository.service.detail.impl.AuditableGeneratedIDSchemaServiceImpl;
import vn.com.mta.science.module.model.Degree;
import vn.com.mta.science.module.schema.DegreeGet;
import vn.com.mta.science.module.service.db.DegreeDAO;

@SuppressWarnings("Duplicates")
@Service("degreeService")
public class DegreeServiceImpl extends AuditableGeneratedIDSchemaServiceImpl<DegreeGet, Degree> implements DegreeService {

    @Autowired
    private DegreeDAO degreeDAO;

    @Override
    public DegreeGet convert(Degree researchGroup) {
        return new DegreeGet(researchGroup);
    }

    @Override
    public AuditableDAO<Degree, Long> getDAO() {
        return degreeDAO;
    }
}
