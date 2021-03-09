package vn.com.mta.science.module.service.db;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.*;
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
                    predicates.add(criteriaInfo.getBuilder().between(criteriaInfo.getRoot().get(Invention_.PUBLICATION_DATE), filter.getStarttime(), filter.getEndtime() + "-99-99"));
                }

            if (filter.getGroupId() != null && !filter.getGroupId().isEmpty())
                predicates.add(criteriaInfo.getRoot().get(Invention_.GROUP_ID).in(filter.getGroupId()));

//            if (filter.getMajorId() != null && !filter.getMajorId().isEmpty())
//                predicates.add(criteriaInfo.getRoot().get(Document_.MAJOR_ID).in(filter.getMajorId()));
//
//            if (filter.getSpecializationId() != null && !filter.getSpecializationId().isEmpty())
//                predicates.add(criteriaInfo.getRoot().get(Document_.SPECIALIZATION_ID).in(filter.getSpecializationId()));
//
//
//            if (filter.getStarttime() != null)
//                if (filter.getEndtime() != null) {
//                    predicates.add(criteriaInfo.getBuilder().between(criteriaInfo.getRoot().get(Document_.PUBLISH_DATE), filter.getStarttime(), filter.getEndtime() + "-12-31"));
//                }
//
            if (filter.getAuthorId() != null && !filter.getAuthorId().isEmpty()) {

                Join<Object, Object> roleJoin = criteriaInfo.getRoot().join(Document_.AUTHORS, JoinType.LEFT);

                for (String authorName : filter.getAuthorId()) {

                    predicates.add(criteriaInfo.getBuilder().like(criteriaInfo.getBuilder().lower(roleJoin.get(Author_.FULLNAME)),
                            "%"+ authorName.toLowerCase() +"%"));
                }
            }
//
            if (filter.getKeyword() != null) {
                predicates.add(criteriaInfo.getBuilder().like(criteriaInfo.getRoot().get(Invention_.DESCRIPTION),
                        "%" + filter.getKeyword() + "%"));
            }
//
            if (filter.getAffiliationId() != null && !filter.getAffiliationId().isEmpty()){
                Join<Object, Object> roleJoin = criteriaInfo.getRoot().join(Invention_.AUTHORS, JoinType.LEFT);
                CriteriaBuilder.In<Long> inListRoleIds = criteriaInfo.getBuilder().in(roleJoin.get(Author_.AFFILIATION_ID));
                for (Long authorName : filter.getAffiliationId())
                    inListRoleIds.value(authorName);
                predicates.add(inListRoleIds);
            }
//


            return predicates;
        }
        return null;
    }

    @Override
    @Cacheable(value = "inventionByUuid", key = "#uuid", unless = "#result == null")
    public Invention getByUuid(String uuid) {
        CriteriaInfo criteriaInfo = getBaseCriteriaInfo();
        criteriaInfo.getCriteria().where(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Invention_.UUID), uuid));

        List<Invention> results = getSession().createQuery(criteriaInfo.getCriteria()).getResultList();
        if (results == null || results.isEmpty()) return null;
        return results.get(0);
    }
}
