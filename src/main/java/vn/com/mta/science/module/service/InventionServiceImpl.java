package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;
import vn.com.itechcorp.base.util.UuidUtil;
import vn.com.itechcorp.telerad.file.io.FileUtil;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.db.*;
import vn.com.mta.science.module.service.filter.AttachmentFilter;
import vn.com.mta.science.module.service.filter.CitedFilter;
import vn.com.mta.science.module.service.filter.DocumentMemberFilter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service("inventionService")
public class InventionServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<InventionGet, Invention> implements InventionService {

    @Autowired
    private InventionDAO inventionDAO;

    @Autowired
    private InventionMemberDAO inventionMemberDAO;

    @Autowired
    private AttachmentInventionDAO attachmentInventionDAO;

    @Override
    public InventionDAO getDAO() {
        return inventionDAO;
    }

    @Override
    public InventionGet convert(Invention invention) {
        InventionGet inventionGet = new InventionGet(invention);
        DocumentMemberFilter documentMemberFilter = new DocumentMemberFilter();
        documentMemberFilter.setDocumentId(invention.getId());
        List<InventionMember> documentMembers = inventionMemberDAO.getPageOfData(documentMemberFilter, null);
        if (documentMembers != null)
            inventionGet.setAuthors(documentMembers.stream().map(InventionMember::getAuthor_id).collect(Collectors.toList()));


        AttachmentFilter attachmentFilter = new AttachmentFilter();
        attachmentFilter.setDocumentId(invention.getId());
        List<AttachmentInvention> attachments = attachmentInventionDAO.getPageOfData(attachmentFilter, null);
        if (attachments != null) {
            inventionGet.setImage(attachments.get(0).getUrl());
        }


        return inventionGet;
    }

    private String attachmentDir;


    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


    private AttachmentInvention saveAttachmentFullText(MultipartFile file, Invention document) throws Exception {
//         create date directory
        attachmentDir = "D:\\Deploy\\invention";
        FileUtil.makeDir(attachmentDir);
        String dateDir = FileUtil.changeDir(attachmentDir, dateFormat.format(document.getDateCreated()));
        String reportDir = FileUtil.changeDir(dateDir, document.getId().toString());

        // save file to report directory
        String type = file.getOriginalFilename().split("\\.")[1];

        File attachment = new File(reportDir + "/" + file.getOriginalFilename());

        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(attachment));
        FileCopyUtils.copy(file.getInputStream(), stream);
        stream.close();

        AttachmentInvention reportAttachment = new AttachmentInvention();
        reportAttachment.setInvention_id(document.getId());
        reportAttachment.setUrl(attachment.getAbsolutePath());
        reportAttachment.setContentType(file.getContentType());
        reportAttachment.setContentSizeKb(file.getSize() / 1024);
        reportAttachment.setType(0L);

        return attachmentInventionDAO.create(reportAttachment, document.getCreator());
    }

    @Override
    public InventionGet create(GeneratedIDSchemaCreate<Invention> entity, Long callerId) throws APIException {
        InventionCreate object = (InventionCreate) entity;

        Invention invention = inventionDAO.create(object.toEntry(), 0L);
        if (object.getAuthors() != null && !object.getAuthors().isEmpty()){
            for (Long id :object.getAuthors()){
                InventionMember inventionMember = new InventionMember();
                inventionMember.setAuthor_id(id);
                inventionMember.setInvention_id(invention.getId());
                inventionMemberDAO.create(inventionMember, 0L);
            }
        }

        if (object.getImage() != null && !object.getImage().isEmpty()) {

            MultipartFile file = object.getImage();
            try {
               saveAttachmentFullText(file, invention);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        InventionGet inventionGet = convert(invention);
        return inventionGet;
    }


    @Override
    public InventionGet update(SchemaUpdate<Invention, Long> entity, Long callerId) throws APIException {
        InventionUpdate object = (InventionUpdate) entity;

        Invention invention = inventionDAO.getById(object.getId());

        AttachmentFilter attachmentFilter = new AttachmentFilter();
        attachmentFilter.setDocumentId(invention.getId());
        attachmentFilter.setType(0L);

        DocumentMemberFilter documentMemberFilter = new DocumentMemberFilter();
        documentMemberFilter.setDocumentId(invention.getId());
        List<InventionMember> documentMembers = inventionMemberDAO.getPageOfData(documentMemberFilter, null);
        if (documentMembers != null)
            for (InventionMember documentMember : documentMembers) {
                inventionMemberDAO.delete(documentMember, 0L);
            }

        if (object.getAuthors() != null && !object.getAuthors().isEmpty()) {
            for (Long id : object.getAuthors()) {
                InventionMember documentMember = new InventionMember();
                documentMember.setAuthor_id(id);
                documentMember.setInvention_id(invention.getId());
                inventionMemberDAO.create(documentMember, invention.getCreator());
            }
        }


        object.apply(invention);
        return convert(getDAO().update(invention, callerId));
    }


}
