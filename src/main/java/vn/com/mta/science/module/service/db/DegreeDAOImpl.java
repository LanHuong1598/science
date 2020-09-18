package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Degree;
import vn.com.mta.science.module.model.Degree_;
import vn.com.mta.science.module.service.filter.AffiliationFilter;
import vn.com.mta.science.module.service.filter.DegreeFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("degreeDAO")
public class DegreeDAOImpl extends VoidableDAOHbnImpl<Degree, Long> implements DegreeDAO {
    @Override
    public Class<Degree> getEntityClass() {
        return Degree.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof AffiliationFilter) {
            List<Predicate> predicates = new ArrayList<>();
            DegreeFilter filter = (DegreeFilter) baseFilter;

            if (filter.getType() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Degree_.TYPE), filter.getType()));

            return predicates;
        }

        return null;
    }
}
