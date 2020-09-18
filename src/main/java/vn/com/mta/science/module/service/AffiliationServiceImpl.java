package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.schema.AffiliationGet;
import vn.com.mta.science.module.service.db.AffiliationDAO;

@SuppressWarnings("Duplicates")
@Service("affiliationService")
public class AffiliationServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<AffiliationGet, Affiliation> implements AffiliationService {

    @Autowired
    private AffiliationDAO affiliationDAO;

    @Override
    public AffiliationDAO getDAO() {
        return affiliationDAO;
    }

    @Override
    public AffiliationGet convert(Affiliation affiliation) {
        return new AffiliationGet(affiliation);
    }
}
