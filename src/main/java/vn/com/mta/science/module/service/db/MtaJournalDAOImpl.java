package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.MtaJournal;

@Repository("mtaJournalDAO")
public class MtaJournalDAOImpl extends AuditableDAOHbnImpl<MtaJournal, Long> implements MtaJournalDAO {

    @Override
    public Class<MtaJournal> getEntityClass() {
        return MtaJournal.class;
    }
}
