package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.AttachmentInvention;
import vn.com.mta.science.module.model.AttachmentInvention_;
import vn.com.mta.science.module.service.filter.AttachmentFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("attachmentInventionDAO")
public class AttachmentInventionDAOImpl extends AuditableDAOHbnImpl<AttachmentInvention, Long> implements AttachmentInventionDAO {

    @Override
    public Class<AttachmentInvention> getEntityClass() {
        return AttachmentInvention.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof AttachmentFilter) {
            List<Predicate> predicates = new ArrayList<>();
            AttachmentFilter filter = (AttachmentFilter) baseFilter;

            if (filter.getDocumentId() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(AttachmentInvention_.INVENTION_ID), filter.getDocumentId()));

            if (filter.getType() != null)
                predicates.add(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(AttachmentInvention_.TYPE), filter.getType()));

            return predicates;
        }

        return null;
    }

}
