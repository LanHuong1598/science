package vn.com.mta.science.module.user.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.exception.IllegalPropertyException;
import vn.com.itechcorp.base.exception.ObjectNotFoundException;
import vn.com.itechcorp.base.exception.UnchangeableObjectException;
import vn.com.itechcorp.base.repository.dao.PaginationInfo;
import vn.com.itechcorp.base.repository.service.detail.impl.AuditableSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;
import vn.com.mta.science.module.user.filter.RoleFilter;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.schema.RoleGet;
import vn.com.mta.science.module.user.service.db.RoleDAO;

import java.util.Collections;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends AuditableSchemaServiceImpl<RoleGet, Role, String> implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public RoleDAO getDAO() {
        return roleDAO;
    }

    @Override
    public RoleGet convert(Role role) {
        return new RoleGet(role);
    }

    @Override
    public List<RoleGet> getPageOfData(PaginationInfo pagingInfo) throws APIException {
        RoleFilter filter = new RoleFilter(Collections.singletonList("sysadmin"));
        return super.getPageOfData(filter, pagingInfo);
    }

    @Override
    public long getCountAll() throws APIException {
        RoleFilter filter = new RoleFilter(Collections.singletonList("sysadmin"));
        return super.getCountAll(filter);
    }

    @Override
    public RoleGet update(SchemaUpdate<Role, String> entity, Long callerId) throws APIException {
        Role existing = getDAO().getById(entity.getId());
        if (existing == null) throw new ObjectNotFoundException("Object not found {id}: " + entity.getId());
        if (existing.getIsDefault())
            throw new UnchangeableObjectException("Cannot update default role {id}: " + existing.getId());

        if (!entity.apply(existing)) return convert(existing);

        try {
            return convert(getDAO().update(existing, callerId));
        } catch (ConstraintViolationException ex) {
            throw new IllegalPropertyException(ex.toString());
        }
    }

    @Override
    public RoleGet deleteByID(String id, Long callerId) throws APIException {
        Role existing = getDAO().getById(id);
        if (existing == null) throw new ObjectNotFoundException("Object not found {id}: " + id);
        if (existing.getIsDefault())
            throw new UnchangeableObjectException("Cannot delete default role {id}: " + existing.getId());

        getDAO().delete(existing, callerId);
        return convert(existing);
    }
}
