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
import vn.com.mta.science.module.model.Attachment;
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.model.DocumentMember;
import vn.com.mta.science.module.schema.AttachmentGet;
import vn.com.mta.science.module.schema.DocumentCreate;
import vn.com.mta.science.module.schema.DocumentGet;
import vn.com.mta.science.module.service.db.AttachmentDAO;
import vn.com.mta.science.module.service.db.DocumentDAO;
import vn.com.mta.science.module.service.db.DocumentMemberDAO;
import vn.com.mta.science.module.service.filter.AttachmentFilter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service("documentService")
public class DocumentServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<DocumentGet, Document> implements DocumentService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private DocumentDAO documentDAO;

    @Autowired
    private AttachmentDAO attachmentDAO;

    @Value("${app.file.reportAttachment:report}")
    private String attachmentDir;

    @Autowired
    private DocumentMemberDAO documentMemberDAO;

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

        List<Attachment> attachments = attachmentDAO.getPageOfData(attachmentFilter,null);
        if (attachments != null) {
            documentGet.setAttachmentsFullText(attachments.stream().map(AttachmentGet::new).collect(Collectors.toList()));
        }


        attachmentFilter.setType(1L);
        attachments = attachmentDAO.getPageOfData(attachmentFilter,null);
        if (attachments != null) {
            documentGet.setAttachmentsAbstract(attachments.stream().map(AttachmentGet::new).collect(Collectors.toList()));
        }
        return documentGet;
    }

    private Attachment saveAttachmentFullText(MultipartFile file, Document document) throws Exception {
//         create date directory
        FileUtil.makeDir(attachmentDir);
        String dateDir = FileUtil.changeDir(attachmentDir, dateFormat.format(document.getDateCreated()));
        String reportDir = FileUtil.changeDir(dateDir, document.getId().toString());

        // save file to report directory
        File attachment = new File(reportDir + "/" + UuidUtil.getNewUuid());

        file.transferTo(attachment);

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
        report = documentDAO.create(report, callerId);

        if (object.getAttachmentsFullText() != null && !object.getAttachmentsFullText().isEmpty()) {
            Set<Attachment> attachments = new HashSet<>();

            for (MultipartFile file : object.getAttachmentsFullText()) {
                try {
                    attachments.add(saveAttachmentFullText(file, report));
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
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
//
//    @Override
//    public ReportGet update(SchemaUpdate<Report, Long> entity, Long callerId) throws APIException {
//        Report report = getDAO().getById(entity.getId());
//        if (report == null) throw new ObjectNotFoundException("Invalid report {id}:" + entity.getId());
//
//        if (report.getCompleted())
//            throw new InvalidOperationOnObjectException("Cannot update completed report {id}:" + entity.getId());
//
//        if (!report.getCreator().equals(callerId)) throw new APIAuthenticationException("Access is denied");
//
//        ReportUpdate object = (ReportUpdate) entity;
//
//        // remove unused files
//        if (object.getPurgedAttachmentIds() != null && !object.getPurgedAttachmentIds().isEmpty()) {
//            List<ReportAttachment> attachments = reportAttachmentDAO.getByReportId(report.getId());
//            if (attachments != null) for (ReportAttachment attachment : attachments)
//                if (object.getPurgedAttachmentIds().contains(attachment.getId())) {
//                    deleteAttachment(attachment);
//                    report.getAttachments().remove(attachment);
//                }
//        }
//
//        // create new files
//        if (object.getNewAttachments() != null) for (MultipartFile file : object.getNewAttachments()) {
//            try {
//                report.getAttachments().add(saveAttachment(file, report));
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                logger.error("Error saving attachment {} for report {}", file.getOriginalFilename(), report.getId());
//            }
//        }
//
//        if (object.getCompleted() == null) object.setCompleted(false);
//
//        if (!entity.apply(report)) convert(report);
//        return convert(getDAO().update(report, callerId));
//    }
//
//    @Override
//    public ReportGet deleteByID(Long id, boolean purge, Long callerId) throws APIException {
//        Report report = getDAO().getById(id);
//        if (report == null) throw new ObjectNotFoundException("Invalid report {id}:" + id);
//        if (!report.getCreator().equals(callerId)) throw new APIAuthenticationException("Access is denied");
//
//        if (report.getCompleted())
//            return new ReportGet(getDAO().voids(report, callerId, "Delete action is called by user " + callerId));
//
//        return new ReportGet(deleteReport(report));
//    }
//
//    @Override
//    public byte[] getAttachment(Long id, Long attachmentId) throws APIException {
//        ReportAttachment attachment = reportAttachmentDAO.getById(attachmentId);
//        if (attachment == null || !attachment.getReportId().equals(id))
//            throw new ObjectNotFoundException("Attachment is not found: " + attachmentId);
//
//        try {
//            URL url = Paths.get(attachment.getUrl()).toUri().toURL();
//            File f = new File(url.toURI());
//            return StreamUtils.copyToByteArray(new FileInputStream(f));
//        } catch (URISyntaxException | IOException e) {
//            throw new APIException(e.toString());
//        }
//    }

}
