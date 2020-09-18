package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.repository.dao.AuditableDAO;
import vn.com.itechcorp.base.repository.service.detail.impl.AuditableGeneratedIDSchemaServiceImpl;
import vn.com.mta.science.module.model.ResearchGroup;
import vn.com.mta.science.module.schema.GroupGet;
import vn.com.mta.science.module.service.db.ResearchGroupDAO;

@SuppressWarnings("Duplicates")
@Service("groupService")
public class GroupServiceImpl extends AuditableGeneratedIDSchemaServiceImpl<GroupGet, ResearchGroup> implements GroupService {

    @Autowired
    private ResearchGroupDAO researchGroupDAO;

    @Override
    public GroupGet convert(ResearchGroup researchGroup) {
        return new GroupGet(researchGroup);
    }

    @Override
    public AuditableDAO<ResearchGroup, Long> getDAO() {
        return researchGroupDAO;
    }
}
