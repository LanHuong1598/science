package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Affiliation_;
import vn.com.mta.science.module.model.GroupMember;
import vn.com.mta.science.module.model.GroupMember_;
import vn.com.mta.science.module.service.filter.AffiliationFilter;
import vn.com.mta.science.module.service.filter.GroupMemberFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("groupMemberDAO")
public class GroupMemberDAOImpl extends AuditableDAOHbnImpl<GroupMember, Long> implements GroupMemberDAO {

    @Override
    public Class<GroupMember> getEntityClass() {
        return GroupMember.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof GroupMemberFilter) {
            List<Predicate> predicates = new ArrayList<>();
            GroupMemberFilter filter = (GroupMemberFilter) baseFilter;

            if (filter.getAuthorId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(GroupMember_.AUTHOR_ID), filter.getAuthorId()));

            return predicates;
        }

        return null;
    }
}
