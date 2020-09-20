package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Major;
import vn.com.mta.science.module.schema.AffiliationGet;
import vn.com.mta.science.module.schema.MajorGet;
import vn.com.mta.science.module.service.db.AffiliationDAO;
import vn.com.mta.science.module.service.db.MajorDAO;

@SuppressWarnings("Duplicates")
@Service("majornService")
public class MajorServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<MajorGet, Major> implements MajorService {

    @Autowired
    private MajorDAO majorDAO;

    @Override
    public MajorDAO getDAO() {
        return majorDAO;
    }

    @Override
    public MajorGet convert(Major affiliation) {
        return new MajorGet(affiliation);
    }
}
