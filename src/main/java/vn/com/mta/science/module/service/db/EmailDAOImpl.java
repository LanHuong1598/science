package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Email;
import vn.com.mta.science.module.model.Email_;
import vn.com.mta.science.module.service.filter.EmailAuthorFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("emailDAO")
public class EmailDAOImpl extends AuditableDAOHbnImpl<Email, Long> implements EmailDAO {

    @Override
    public Class<Email> getEntityClass() {
        return Email.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof EmailAuthorFilter) {
            List<Predicate> predicates = new ArrayList<>();
            EmailAuthorFilter filter = (EmailAuthorFilter) baseFilter;

             if (filter.getAuthorId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Email_.AUTHOR_ID), filter.getAuthorId()));

            return predicates;
        }

        return null;
    }

}
