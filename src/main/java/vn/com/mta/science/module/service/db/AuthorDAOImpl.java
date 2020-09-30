package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Affiliation_;
import vn.com.mta.science.module.model.Author;
import vn.com.mta.science.module.model.Author_;
import vn.com.mta.science.module.service.filter.AffiliationFilter;
import vn.com.mta.science.module.service.filter.AuthorByAffFilter;
import vn.com.mta.science.module.service.filter.AuthorFilter;

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

        return null;
    }
}
