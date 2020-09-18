package vn.com.mta.science.module.service.db;

import vn.com.itechcorp.base.repository.dao.AuditableDAO;
import vn.com.mta.science.module.model.Attachment;
import vn.com.mta.science.module.schema.DocumentGet;

import java.util.List;

public interface AttachmentDAO extends AuditableDAO<Attachment, Long> {
}
