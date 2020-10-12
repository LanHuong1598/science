package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import javax.print.Doc;
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
@Service("documentService")
@Transactional
public class DocumentServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<DocumentGet, Document> implements DocumentService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private DocumentDAO documentDAO;

    @Autowired
    private DocumentReplicaDAO documentReplicaDAO;

    @Autowired
    private AttachmentDAO attachmentDAO;

    @Value("${app.file.reportAttachment:report}")
    private String attachmentDir;

    @Autowired
    private DocumentMemberDAO documentMemberDAO;

    @Autowired
    private MajorDAO majorDAO;

    @Autowired
    private CitedDAO citedDAO;

    @Override
    public DocumentDAO getDAO() {
        return documentDAO;
    }

    @Override
    public DocumentGet convert(Document document) {
        DocumentGet documentGet = new DocumentGet(document);
        AttachmentFilter attachmentFilter = new AttachmentFilter();
        attachmentFilter.setDocumentId(document.getId());
        attachmentFilter.setType(0L);

        List<Attachment> attachments = attachmentDAO.getPageOfData(attachmentFilter, null);
        if (attachments != null) {
            documentGet.setAttachmentsFullText(attachments.stream().map(AttachmentGet::new).collect(Collectors.toList()));
        }

        if (document.getKeyword() != null) {
            String[] values = document.getKeyword().split(",");
            documentGet.setKeyword(Arrays.stream(values).collect(Collectors.toList()));
        }

        attachmentFilter.setType(1L);
        attachments = attachmentDAO.getPageOfData(attachmentFilter, null);
        if (attachments != null) {
            documentGet.setAttachmentsAbstract(attachments.stream().map(AttachmentGet::new).collect(Collectors.toList()));
        }

        CitedFilter citedFilter = new CitedFilter();
        citedFilter.setDocumentId(document.getId());
        List<Cited> citeds = citedDAO.getPageOfData(citedFilter, null);
        if (citeds != null)
            documentGet.setCiteds(citeds.stream().map(CitedGet::new).collect(Collectors.toList()));

        DocumentMemberFilter documentMemberFilter = new DocumentMemberFilter();
        documentMemberFilter.setDocumentId(document.getId());
        List<DocumentMember> documentMembers = documentMemberDAO.getPageOfData(documentMemberFilter, null);
        if (documentMembers != null)
            documentGet.setAuthors(documentMembers.stream().map(DocumentMember::getAuthor_id).collect(Collectors.toList()));

        return documentGet;
    }

    private Attachment saveAttachmentFullText(MultipartFile file, Document document) throws Exception {
//         create date directory
        FileUtil.makeDir(attachmentDir);
        String dateDir = FileUtil.changeDir(attachmentDir, dateFormat.format(document.getDateCreated()));
        String reportDir = FileUtil.changeDir(dateDir, document.getId().toString());

        // save file to report directory
        File attachment = new File(reportDir + "/" + UuidUtil.getNewUuid());
        String type = file.getOriginalFilename().split("\\.")[1];

        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(new File(reportDir + "/" + UuidUtil.getNewUuid() + "." + type)));
        FileCopyUtils.copy(file.getInputStream(), stream);
        stream.close();

        Attachment reportAttachment = new Attachment();
        reportAttachment.setDocument_id(document.getId());
        reportAttachment.setUrl(attachment.getAbsolutePath());
        reportAttachment.setContentType(file.getContentType());
        reportAttachment.setContentSizeKb(file.getSize() / 1024);
        reportAttachment.setType(0L);

        return attachmentDAO.create(reportAttachment, document.getCreator());
    }

    private Attachment saveAttachmentAbstract(MultipartFile file, Document document) throws Exception {
//         create date directory
        FileUtil.makeDir(attachmentDir);
        String dateDir = FileUtil.changeDir(attachmentDir, dateFormat.format(document.getDateCreated()));
        String reportDir = FileUtil.changeDir(dateDir, document.getId().toString());

        // save file to report directory
        File attachment = new File(reportDir + "/" + UuidUtil.getNewUuid());
        String type = file.getOriginalFilename().split("\\.")[1];

        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(new File(reportDir + "/" + UuidUtil.getNewUuid() + "." + type)));
        FileCopyUtils.copy(file.getInputStream(), stream);
        stream.close();

        file.transferTo(attachment);

        Attachment reportAttachment = new Attachment();
        reportAttachment.setDocument_id(document.getId());
        reportAttachment.setUrl(attachment.getAbsolutePath());
        reportAttachment.setContentType(file.getContentType());
        reportAttachment.setContentSizeKb(file.getSize() / 1024);
        reportAttachment.setType(1L);

        return attachmentDAO.create(reportAttachment, document.getCreator());
    }

    private void deleteAttachment(Attachment attachment) {
        attachmentDAO.delete(attachment, 0L);
        File file = new File(attachment.getUrl());
        if (file.exists()) file.delete();
    }

    @Override
    public DocumentGet create(GeneratedIDSchemaCreate<Document> entity, Long callerId) throws APIException {
        DocumentCreate object = (DocumentCreate) entity;

        Document report = object.toEntry();

        if (object.getKeyword() != null && !object.getKeyword().isEmpty()) {
            String ss = "";
            for (String s : object.getKeyword())
                ss = ss + "," + s;
            report.setKeyword(ss);
        }

        report = documentDAO.create(report, callerId);
        DocumentReplica documentReplica = new DocumentReplica(report);
        if (report.getMajorId() != null) {
            Major major = majorDAO.getById(report.getMajorId());
            documentReplica.setChuyennganhId(report.getMajorId());
            if (major.getParentId() != null &&  major.getParentId() != 0) {
                documentReplica.setNganhId(major.getParentId());
            }
        }



        if (object.getAttachmentsFullText() != null && !object.getAttachmentsFullText().isEmpty()) {
            Set<Attachment> attachments = new HashSet<>();

            MultipartFile file = object.getAttachmentsFullText();
            try {
                attachments.add(saveAttachmentFullText(file, report));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if (object.getAttachmentsAbstract() != null && !object.getAttachmentsAbstract().isEmpty()) {
            Set<Attachment> attachments = new HashSet<>();

            for (MultipartFile file : object.getAttachmentsAbstract()) {
                try {
                    attachments.add(saveAttachmentAbstract(file, report));
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        }

        if (object.getAuthors() != null && !object.getAuthors().isEmpty()) {
            for (Long id : object.getAuthors()) {
                DocumentMember documentMember = new DocumentMember();
                documentMember.setAuthor_id(id);
                documentMember.setDocument_id(report.getId());
                documentMemberDAO.create(documentMember, report.getCreator());
            }
        }

        DocumentGet reportGet = convert(report);

        return reportGet;
    }

    @Override
    public DocumentGet update(SchemaUpdate<Document, Long> entity, Long callerId) throws APIException {
        DocumentUpdate object = (DocumentUpdate) entity;

        Document document = documentDAO.getById(object.getId());

        if (object.getKeyword() != null && !object.getKeyword().isEmpty()) {
            String ss = "";
            for (String s : object.getKeyword())
                ss = ss + "," + s;

            document.setKeyword(ss);
        }

        AttachmentFilter attachmentFilter = new AttachmentFilter();
        attachmentFilter.setDocumentId(document.getId());
        attachmentFilter.setType(0L);

        List<Attachment> attachments = attachmentDAO.getPageOfData(attachmentFilter, null);
        if (attachments != null) {
            for (Attachment attachment : attachments) {
                attachmentDAO.delete(attachment, 0L);
            }
        }

        attachmentFilter.setType(1L);
        attachments = attachmentDAO.getPageOfData(attachmentFilter, null);
        if (attachments != null) {
            if (attachments != null) {
                for (Attachment attachment : attachments) {
                    attachmentDAO.delete(attachment, 0L);
                }
            }
        }

        CitedFilter citedFilter = new CitedFilter();
        citedFilter.setDocumentId(document.getId());
        List<Cited> citeds = citedDAO.getPageOfData(citedFilter, null);
        if (citeds != null)
            for (Cited cited : citeds) {
                citedDAO.delete(cited, 0L);
            }

        DocumentMemberFilter documentMemberFilter = new DocumentMemberFilter();
        documentMemberFilter.setDocumentId(document.getId());
        List<DocumentMember> documentMembers = documentMemberDAO.getPageOfData(documentMemberFilter, null);
        if (documentMembers != null)
            for (DocumentMember documentMember : documentMembers) {
                documentMemberDAO.delete(documentMember, 0L);
            }


        if (object.getAttachmentsFullText() != null && !object.getAttachmentsFullText().isEmpty()) {
            Set<Attachment> attachmentsAdd = new HashSet<>();

            MultipartFile file = object.getAttachmentsFullText();
            try {
                attachmentsAdd.add(saveAttachmentFullText(file, document));
            } catch (Exception ex) {
                ex.printStackTrace();

            }

        }

        if (object.getAttachmentsAbstract() != null && !object.getAttachmentsAbstract().isEmpty()) {
            Set<Attachment> attachmentsAdd = new HashSet<>();

            for (MultipartFile file : object.getAttachmentsAbstract()) {
                try {
                    attachmentsAdd.add(saveAttachmentAbstract(file, document));
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        }

        if (object.getAuthors() != null && !object.getAuthors().isEmpty()) {
            for (Long id : object.getAuthors()) {
                DocumentMember documentMember = new DocumentMember();
                documentMember.setAuthor_id(id);
                documentMember.setDocument_id(document.getId());
                documentMemberDAO.create(documentMember, document.getCreator());
            }
        }

        return convert(getDAO().update(document, callerId));
    }


}
