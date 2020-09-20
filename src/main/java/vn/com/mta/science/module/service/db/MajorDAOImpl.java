package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Affiliation_;
import vn.com.mta.science.module.model.Major;
import vn.com.mta.science.module.model.Major_;
import vn.com.mta.science.module.service.filter.AffiliationFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("majorDAO")
public class MajorDAOImpl extends VoidableDAOHbnImpl<Major, Long> implements MajorDAO {
    @Override
    public Class<Major> getEntityClass() {
        return Major.class;
    }
    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof AffiliationFilter) {
            List<Predicate> predicates = new ArrayList<>();
            AffiliationFilter filter = (AffiliationFilter) baseFilter;

            if (filter.getLevel() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Major_.LEVEL), filter.getLevel()));

            if (filter.getParentId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Major_.PARENT_ID), filter.getParentId()));

            return predicates;
        }

        return null;
    }
}
