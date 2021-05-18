package vn.com.mta.science.module.service.db;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.VoidableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.service.filter.DocumentFilter;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository("documentDAO")
public class DocumentDAOImpl extends VoidableDAOHbnImpl<Document, Long> implements DocumentDAO {
    @Override
    public Class<Document> getEntityClass() {
        return Document.class;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaInfo criteriaInfo, BaseFilter baseFilter) {
        if (baseFilter == null) return null;

        if (baseFilter instanceof DocumentFilter) {
            List<Predicate> predicates = new ArrayList<>();
            DocumentFilter filter = (DocumentFilter) baseFilter;

            if (filter.getClassificationId() != null && !filter.getClassificationId().isEmpty())
                predicates.add(criteriaInfo.getRoot().get(Document_.CLASSIFICATION_ID).in(filter.getClassificationId()));

            if (filter.getDocumentType() != null && !filter.getDocumentType().isEmpty())
                predicates.add(criteriaInfo.getRoot().get(Document_.DOCUMENT_TYPE).in(filter.getDocumentType()));

            if (filter.getGroupId() != null && !filter.getGroupId().isEmpty())
                predicates.add(criteriaInfo.getRoot().get(Document_.GROUP_ID).in(filter.getGroupId()));

            if (filter.getMajorId() != null && !filter.getMajorId().isEmpty())
                predicates.add(criteriaInfo.getRoot().get(Document_.MAJOR_ID).in(filter.getMajorId()));

            if (filter.getSpecializationId() != null && !filter.getSpecializationId().isEmpty())
                predicates.add(criteriaInfo.getRoot().get(Document_.SPECIALIZATION_ID).in(filter.getSpecializationId()));


            if (filter.getStarttime() != null)
                if (filter.getEndtime() != null) {
                    predicates.add(criteriaInfo.getBuilder().between(criteriaInfo.getRoot().get(Document_.PUBLISH_DATE), filter.getStarttime(), filter.getEndtime() + "-99-99"));
                }

            if (filter.getAuthorId() != null && !filter.getAuthorId().isEmpty()) {

                Join<Object, Object> roleJoin = criteriaInfo.getRoot().join(Document_.AUTHORS, JoinType.LEFT);

                for (String authorName : filter.getAuthorId()) {

                    predicates.add(criteriaInfo.getBuilder().like(criteriaInfo.getBuilder().lower(roleJoin.get(Author_.FULLNAME)),
                            "%"+ authorName.toLowerCase() +"%"));
                }
            }

            if (filter.getKeyword() != null) {
                predicates.add(criteriaInfo.getBuilder().like(criteriaInfo.getRoot().get(Document_.KEYWORD),
                        "%" + filter.getKeyword() + "%"));
            }

            if (filter.getAffiliationId() != null && !filter.getAffiliationId().isEmpty()){
                    Join<Object, Object> roleJoin = criteriaInfo.getRoot().join(Document_.AUTHORS, JoinType.LEFT);
                    CriteriaBuilder.In<Long> inListRoleIds = criteriaInfo.getBuilder().in(roleJoin.get(Author_.AFFILIATION_ID));
                    for (Long authorName : filter.getAffiliationId())
                        inListRoleIds.value(authorName);
                    predicates.add(inListRoleIds);
                }

            return predicates;
        }

        return null;
    }

    @Override
    @CacheEvict(value = "documentCache", key = "#entity.id")
    public Document create(Document entity, Long callerId) {
        return super.create(entity, callerId);
    }

    @Override
    @Cacheable(value = "documentCache", key = "#id", unless = "#result == null")
    public Document getById(Long id) {
        return super.getById(id);
    }

    @Override
    @CacheEvict(value = "documentCache", key = "#entity.id")
    public Document update(Document entity, Long callerId) {
        return super.update(entity, callerId);
    }

    @Override
    @CacheEvict(value = "documentCache", key = "#entity.id")
    public void delete(Document entity, Long callerId) {
        super.delete(entity, callerId);
    }

    @Override
    @CacheEvict(value = "documentCache", key = "#entity.id")
    public Document voids(Document entity, Long callerId, String reason) {
        return super.voids(entity, callerId, reason);
    }

    @Override
    @Cacheable(value = "documentByUuid", key = "#uuid", unless = "#result == null")
    public Document getByUuid(String uuid) {
        CriteriaInfo criteriaInfo = getBaseCriteriaInfo();
        criteriaInfo.getCriteria().where(criteriaInfo.getBuilder().equal(criteriaInfo.getRoot().get(Document_.UUID), uuid));

        List<Document> results = getSession().createQuery(criteriaInfo.getCriteria()).getResultList();
        if (results == null || results.isEmpty()) return null;
        return results.get(0);
    }

}
