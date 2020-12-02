package vn.com.mta.science.module.user.service;

import vn.com.itechcorp.base.repository.service.detail.AuditableSchemaService;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.schema.RoleGet;

public interface RoleService extends AuditableSchemaService<RoleGet, Role, String> {
}
