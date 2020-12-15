package vn.com.mta.science.module.service.db;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Major;
import vn.com.mta.science.module.model.Major_;
import vn.com.mta.science.module.service.filter.MajorFilter;

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

        if (baseFilter instanceof MajorFilter) {
            List<Predicate> predicates = new ArrayList<>();
            MajorFilter filter = (MajorFilter) baseFilter;

            if (filter.getLevel() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Major_.LEVEL), filter.getLevel()));

            if (filter.getParentId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Major_.PARENT_ID), filter.getParentId()));

            return predicates;
        }

        return null;
    }


    @Override
    @CachePut(value = "majorCache", key = "#entity.id")
    public Major create(Major entity, Long callerId) {
        return super.create(entity, callerId);
    }

    @Override
    @Cacheable(value = "majorCache", key = "#id", unless = "#result == null")
    public Major getById(Long id) {
        return super.getById(id);
    }

    @Override
    @CachePut(value = "majorCache", key = "#entity.id")
    public Major update(Major entity, Long callerId) {
        return super.update(entity, callerId);
    }

    @Override
    @CacheEvict(value = "majorCache", key = "#entity.id")
    public void delete(Major entity, Long callerId) {
        super.delete(entity, callerId);
    }

    @Override
    @CacheEvict(value = "majorCache", key = "#entity.id")
    public Major voids(Major entity, Long callerId, String reason) {
        return super.voids(entity, callerId, reason);
    }
}
