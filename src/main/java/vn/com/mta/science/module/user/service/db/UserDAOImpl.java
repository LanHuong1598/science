package vn.com.mta.science.module.user.service.db;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Affiliation_;
import vn.com.mta.science.module.user.filter.UserFilter;
import vn.com.mta.science.module.user.model.Role_;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.module.user.model.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("userDAO")
public class UserDAOImpl extends VoidableDAOHbnImpl<User, Long> implements UserDAO {
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof UserFilter) {
            List<Predicate> predicates = new ArrayList<>();
            UserFilter filter = (UserFilter) baseFilter;

            Join<User, Affiliation> join = criteriaInfo.getRoot().join(User_.AFFILIATION);

            if (filter.getKeyword() != null) {
                predicates.add(
                        criteriaInfo.getBuilder().or(
                                criteriaInfo.getBuilder().like(criteriaInfo.getBuilder().lower(criteriaInfo.getRoot().get(User_.FULL_NAME)), "%" + filter.getKeyword().toLowerCase() + "%"),
                                criteriaInfo.getBuilder().like(criteriaInfo.getBuilder().lower(criteriaInfo.getRoot().get(User_.USERNAME)), "%" + filter.getKeyword().toLowerCase() + "%"),
                                criteriaInfo.getBuilder().like(criteriaInfo.getBuilder().lower(join.get(Affiliation_.NAME)), "%" + filter.getKeyword().toLowerCase() + "%")));
            }

            return predicates;
        }
        return null;
    }

    @Override
    @Cacheable(value = "userCacheByUserName", key = "#username", unless = "#result == null")
    public User getByUserName(String username) {
        CriteriaInfo criteriaInfo = getBaseCriteriaInfo();
        criteriaInfo.getCriteria().where(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(User_.USERNAME), username));

        List<User> results = getSession().createQuery(criteriaInfo.getCriteria()).getResultList();
        if (results == null || results.isEmpty()) return null;
        return results.get(0);
    }

    @Override
    @CacheEvict(value = "userCacheById", key = "#id")
    public User getById(Long id) {
        return super.getById(id);
    }

    @Override
    @CachePut(value = "userCacheById", key = "#entity.id")
    public User create(User entity, Long callerId) {
        return super.create(entity, callerId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userCacheByUserName", key = "#entity.username"),
            @CacheEvict(value = "userCacheById", key = "#entity.id")
    })
    public User update(User entity, Long callerId) {
        return super.update(entity, callerId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userCacheByUserName", key = "#entity.username"),
            @CacheEvict(value = "userCacheById", key = "#entity.id")
    })
    public void delete(User entity, Long callerId) {
        super.delete(entity, callerId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userCacheByUserName", key = "#entity.username"),
            @CacheEvict(value = "userCacheById", key = "#entity.id")
    })
    public User voids(User entity, Long callerId, String reason) {
        return super.voids(entity, callerId, reason);
    }
}
