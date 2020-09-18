package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.dao.AuditableDAO;
import vn.com.itechcorp.base.repository.service.detail.impl.AuditableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.util.UuidUtil;
import vn.com.itechcorp.telerad.file.io.FileUtil;
import vn.com.mta.science.module.model.Attachment;
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.model.DocumentMember;
import vn.com.mta.science.module.model.ResearchGroup;
import vn.com.mta.science.module.schema.DocumentCreate;
import vn.com.mta.science.module.schema.DocumentGet;
import vn.com.mta.science.module.schema.GroupGet;
import vn.com.mta.science.module.service.db.AttachmentDAO;
import vn.com.mta.science.module.service.db.DocumentDAO;
import vn.com.mta.science.module.service.db.DocumentMemberDAO;
import vn.com.mta.science.module.service.db.ResearchGroupDAO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

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
