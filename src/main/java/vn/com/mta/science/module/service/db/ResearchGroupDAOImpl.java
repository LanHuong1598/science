package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.mta.science.module.model.ResearchGroup;

@Repository("researchGroupDAO")
public class ResearchGroupDAOImpl extends VoidableDAOHbnImpl<ResearchGroup, Long> implements ResearchGroupDAO {
    @Override
    public Class<ResearchGroup> getEntityClass() {
        return ResearchGroup.class;
    }
}
