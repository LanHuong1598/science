package vn.com.mta.science.module.service;

import vn.com.itechcorp.base.repository.service.detail.AuditableGeneratedIDSchemaService;
import vn.com.itechcorp.base.repository.service.detail.VoidableGeneratedIDSchemaService;
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.model.ResearchGroup;
import vn.com.mta.science.module.schema.DocumentGet;
import vn.com.mta.science.module.schema.GroupGet;


public interface GroupService extends AuditableGeneratedIDSchemaService<GroupGet, ResearchGroup> {

}
