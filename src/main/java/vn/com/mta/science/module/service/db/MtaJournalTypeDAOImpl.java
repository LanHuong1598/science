package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.MtaJournalType;

@Repository("mtaJournalTypeDAO")
public class MtaJournalTypeDAOImpl extends AuditableDAOHbnImpl<MtaJournalType, Long> implements MtaJournalTypeDAO {

    @Override
    public Class<MtaJournalType> getEntityClass() {
        return MtaJournalType.class;
    }
}
