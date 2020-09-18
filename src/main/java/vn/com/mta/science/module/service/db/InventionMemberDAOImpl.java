package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.InventionMember;

@Repository("inventionMemberDAO")
public class InventionMemberDAOImpl extends AuditableDAOHbnImpl<InventionMember, Long> implements InventionMemberDAO {

    @Override
    public Class<InventionMember> getEntityClass() {
        return InventionMember.class;
    }
}
