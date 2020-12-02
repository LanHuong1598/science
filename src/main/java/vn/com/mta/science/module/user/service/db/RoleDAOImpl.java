package vn.com.mta.science.module.user.service.db;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.user.filter.RoleFilter;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.Role_;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("roleDAO")
public class RoleDAOImpl extends AuditableDAOHbnImpl<Role, String> implements RoleDAO {

    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if(baseFilter instanceof RoleFilter) {
            List<Predicate> predicates = new ArrayList<>();
            RoleFilter filter = (RoleFilter) baseFilter;

            if (filter.getExcludedIds() != null && !filter.getExcludedIds().isEmpty())
                    predicates.add(criteriaInfo.getBuilder().not(criteriaInfo.getRoot().get(Role_.id).in(filter.getExcludedIds())));

            return predicates;
        }
        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userCacheByName", allEntries = true),
            @CacheEvict(value = "userCacheById", allEntries = true)
    })
    public Role update(Role entity, Long callerId) {
        return super.update(entity, callerId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userCacheByName", allEntries = true),
            @CacheEvict(value = "userCacheById", allEntries = true)
    })
    public void delete(Role object, Long callerId) {
        super.delete(object, callerId);
    }
}
