package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Cited;
import vn.com.mta.science.module.model.Cited_;
import vn.com.mta.science.module.service.filter.CitedFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("citedDAO")
public class CitedDAOImpl extends AuditableDAOHbnImpl<Cited, Long> implements CitedDAO {

    @Override
    public Class<Cited> getEntityClass() {
        return Cited.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof CitedFilter) {
            List<Predicate> predicates = new ArrayList<>();
            CitedFilter filter = (CitedFilter) baseFilter;

            if (filter.getDocumentId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Cited_.DOCUMENT_ID), filter.getDocumentId()));

            return predicates;
        }

        return null;
    }
}
