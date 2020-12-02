package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.CriteriaInfo;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.model.DocumentMember;
import vn.com.mta.science.module.model.DocumentMemberReplica;
import vn.com.mta.science.module.model.DocumentMember_;
import vn.com.mta.science.module.service.filter.DocumentMemberFilter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository("documentMemberReplicaDAO")
public class DocumentMemberReplicaDAOImpl extends AuditableDAOHbnImpl<DocumentMemberReplica, Long> implements DocumentMemberReplicaDAO {

    @Override
    public Class<DocumentMemberReplica> getEntityClass() {
        return DocumentMemberReplica.class;
    }
}
