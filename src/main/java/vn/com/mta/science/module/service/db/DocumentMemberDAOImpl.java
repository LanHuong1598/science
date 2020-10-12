package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Cited_;
import vn.com.mta.science.module.model.DocumentMember;
import vn.com.mta.science.module.model.DocumentMember_;
import vn.com.mta.science.module.service.filter.CitedFilter;
import vn.com.mta.science.module.service.filter.DocumentMemberFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("documentMemberDAO")
public class DocumentMemberDAOImpl extends AuditableDAOHbnImpl<DocumentMember, Long> implements DocumentMemberDAO {

    @Override
    public Class<DocumentMember> getEntityClass() {
        return DocumentMember.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof DocumentMemberFilter) {
            List<Predicate> predicates = new ArrayList<>();
            DocumentMemberFilter filter = (DocumentMemberFilter) baseFilter;

            if (filter.getDocumentId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(DocumentMember_.DOCUMENT_ID), filter.getDocumentId()));

            return predicates;
        }

        return null;
    }
}
