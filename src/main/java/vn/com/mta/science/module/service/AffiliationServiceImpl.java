package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.util.UuidUtil;
import vn.com.itechcorp.telerad.file.io.FileUtil;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Attachment;
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.model.DocumentMember;
import vn.com.mta.science.module.schema.AffiliationGet;
import vn.com.mta.science.module.schema.DocumentCreate;
import vn.com.mta.science.module.schema.DocumentGet;
import vn.com.mta.science.module.service.db.AffiliationDAO;
import vn.com.mta.science.module.service.db.AttachmentDAO;
import vn.com.mta.science.module.service.db.DocumentDAO;
import vn.com.mta.science.module.service.db.DocumentMemberDAO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("Duplicates")
@Service("affiliationService")
public class AffiliationServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<AffiliationGet, Affiliation> implements AffiliationService {

    @Autowired
    private AffiliationDAO affiliationDAO;

    @Override
    public AffiliationDAO getDAO() {
        return affiliationDAO;
    }

    @Override
    public AffiliationGet convert(Affiliation affiliation) {
        return new AffiliationGet(affiliation);
    }
}
