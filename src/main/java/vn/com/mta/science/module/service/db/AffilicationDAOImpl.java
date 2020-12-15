package vn.com.mta.science.module.service.db;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Affiliation_;
import vn.com.mta.science.module.service.filter.AffiliationFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("affiliationDAO")
public class AffilicationDAOImpl extends VoidableDAOHbnImpl<Affiliation, Long> implements AffiliationDAO {
    @Override
    public Class<Affiliation> getEntityClass() {
        return Affiliation.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof AffiliationFilter) {
            List<Predicate> predicates = new ArrayList<>();
            AffiliationFilter filter = (AffiliationFilter) baseFilter;

            if (filter.getLevel() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Affiliation_.LEVEL), filter.getLevel()));

            if (filter.getParentId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Affiliation_.PARENT_ID), filter.getParentId()));

            return predicates;
        }

        return null;
    }

    @Override
    @CachePut(value = "affCache", key = "#entity.id")
    public Affiliation create(Affiliation entity, Long callerId) {
        return super.create(entity, callerId);
    }

    @Override
    @Cacheable(value = "affCache", key = "#id", unless = "#result == null")
    public Affiliation getById(Long id) {
        return super.getById(id);
    }

    @Override
    @CachePut(value = "affCache", key = "#entity.id")
    public Affiliation update(Affiliation entity, Long callerId) {
        return super.update(entity, callerId);
    }

    @Override
    @CacheEvict(value = "affCache", key = "#entity.id")
    public void delete(Affiliation entity, Long callerId) {
        super.delete(entity, callerId);
    }

    @Override
    @CacheEvict(value = "affCache", key = "#entity.id")
    public Affiliation voids(Affiliation entity, Long callerId, String reason) {
        return super.voids(entity, callerId, reason);
    }

}
