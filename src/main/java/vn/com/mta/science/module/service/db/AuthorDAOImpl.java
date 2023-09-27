package vn.com.mta.science.module.service.db;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Author;
import vn.com.mta.science.module.model.Author_;
import vn.com.mta.science.module.model.Document_;
import vn.com.mta.science.module.service.filter.AuthorFilter;
import vn.com.mta.science.module.service.filter.AuthorFilterByName;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("authorDAO")
public class AuthorDAOImpl extends VoidableDAOHbnImpl<Author, Long> implements AuthorDAO {
    @Override
    public Class<Author> getEntityClass() {
        return Author.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof AuthorFilter) {
            List<Predicate> predicates = new ArrayList<>();

            AuthorFilter filter = (AuthorFilter) baseFilter;
            if (filter.getIds() != null && !filter.getIds().isEmpty())
                predicates.add(criteriaInfo.getRoot().get(Author_.AFFILIATION_ID).in(filter.getIds()));

            return predicates;
        }

        if (baseFilter instanceof AuthorFilterByName) {
            List<Predicate> predicates = new ArrayList<>();

            AuthorFilterByName filter = (AuthorFilterByName) baseFilter;
            if (filter.getName() != null && filter.getName() != "")
                predicates.add(criteriaInfo.getBuilder().like(criteriaInfo.getRoot().get(Author_.FULLNAME),
                        "%" + filter.getName() + "%"));
            return predicates;
        }

        return null;
    }

}
