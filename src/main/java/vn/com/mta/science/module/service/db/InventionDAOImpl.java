package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.service.filter.DocumentFilter;
import vn.com.mta.science.module.service.filter.DocumentMemberFilter;
import vn.com.mta.science.module.service.filter.InventionFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("inventionDAO")
public class InventionDAOImpl extends VoidableDAOHbnImpl<Invention, Long> implements InventionDAO {
    @Override
    public Class<Invention> getEntityClass() {
        return Invention.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof InventionFilter) {
            List<Predicate> predicates = new ArrayList<>();
            InventionFilter filter = (InventionFilter) baseFilter;


            if (filter.getType() != null) {
                predicates.add(criteriaInfo.getBuilder().like(criteriaInfo.getRoot().get(Invention_.TYPE),
                        "%" + filter.getType() + "%"));
            }

            if (filter.getStarttime() != null)
                if (filter.getEndtime() != null) {
                    predicates.add(criteriaInfo.getBuilder().between(criteriaInfo.getRoot().get(Invention_.PUBLICATION_DATE), filter.getStarttime(), filter.getEndtime() + "-12-31"));
                }


            return predicates;
        }
        return null;
    }
}
