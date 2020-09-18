package vn.com.mta.science.module.service.db;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.repository.dao.hibernate.AuditableDAOHbnImpl;
import vn.com.mta.science.module.model.AttachmentInvention;

@Repository("attachmentInventionDAO")
public class AttachmentInventionDAOImpl extends AuditableDAOHbnImpl<AttachmentInvention, Long> implements AttachmentInventionDAO {

    @Override
    public Class<AttachmentInvention> getEntityClass() {
        return AttachmentInvention.class;
    }
}
