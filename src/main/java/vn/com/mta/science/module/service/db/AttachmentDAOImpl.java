package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.Attachment;
import vn.com.mta.science.module.model.Attachment_;
import vn.com.mta.science.module.service.filter.AttachmentFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("attachmentDAO")
public class AttachmentDAOImpl extends AuditableDAOHbnImpl<Attachment, Long> implements AttachmentDAO {

    @Override
    public Class<Attachment> getEntityClass() {
        return Attachment.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof AttachmentFilter) {
            List<Predicate> predicates = new ArrayList<>();
            AttachmentFilter filter = (AttachmentFilter) baseFilter;

            if (filter.getDocumentId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Attachment_.DOCUMENT_ID), filter.getDocumentId()));

            if (filter.getType() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Attachment_.TYPE), filter.getType()));

            return predicates;
        }

        return null;
    }

}
