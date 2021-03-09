package vn.com.mta.science.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.dao.PaginationInfo;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;
import vn.com.itechcorp.base.util.UuidUtil;
import vn.com.itechcorp.telerad.file.io.FileUtil;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.db.*;
import vn.com.mta.science.module.service.filter.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private DocumentMemberReplicaDAO documentMemberReplicaDAO;

    @Autowired
    private MajorDAO majorDAO;

    @Autowired
    private CitedDAO citedDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private ClassificationDAO classificationDAO;

    @Autowired
    private DocumentTypeDAO documentTypeDAO;

    @Autowired
    private ResearchGroupDAO researchGroupDAO;


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
            documentGet.setAttachmentsFullText(new AttachmentGet(attachments.get(0)));
            documentGet.setUrl(attachments.get(0).getUrl());
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
        {
            documentGet.setAuthors(documentMembers.stream().map(DocumentMember::getAuthorId).collect(Collectors.toList()));

            documentGet.setAuthorsName(new ArrayList<>());
            for (DocumentMember id : documentMembers){
                Author author = authorDAO.getById(id.getAuthorId());
                if (author != null) {
                    documentGet.getAuthorsName().add(author.getFullname());
                }
            }

        }

//        if (document.getMajorId() != null) {
//            Major major = majorDAO.getById(document.getMajorId());
//            if (major != null) {
//                if (major.getParentId() != null && major.getParentId() != 0){
//                    major = majorDAO.getById(major.getParentId());
//                    if (major != null){
//                        documentGet.setParentMajorId(major.getId());
//                    }
//                }
//            }
//        }
//
//        Classification classification = classificationDAO.getById(document.getClassificationId());
//        if (classification != null) {
//            documentGet.setClassificationName(classification.getName());
//        }
//
//        DocumentType documentType = documentTypeDAO.getById(document.getDocumentType());
//        if (documentType != null) documentGet.setDocumentTypeName(documentType.getName());
//
//        ResearchGroup researchGroup = researchGroupDAO.getById(document.getGroupId());
//        if (researchGroup != null) documentGet.setGroupName(researchGroup.getName());
//
//
//        Major major = majorDAO.getById(document.getMajorId());
//        if (major != null) documentGet.setMajorName(major.getName());
//
//        Major major1 = majorDAO.getById(document.getSpecializationId());
//        if (major1 != null) documentGet.setSpecializationName(major1.getName());


        return documentGet;
    }

    @Override
    public DocumentGet getByUuid(String uuid){
        return convert(documentDAO.getByUuid(uuid));
    }

    @Override
    public List<DocumentGet> getPageOfData(BaseFilter filter, PaginationInfo pageinfo) {
        if (filter instanceof DocumentTotalFilter) {

            DocumentTotalFilter ft = (DocumentTotalFilter) filter;

            DocumentFilter lt = new DocumentFilter();

            if (ft.getAuthorId() != null && !ft.getAuthorId().equals("")) {
                Set<String> author = new HashSet<>();
                author.add(ft.getAuthorId());
                lt.setAuthorId(author);
            }

            if (ft.getClassificationId() != null) {
                Set<Long> author = new HashSet<>();

                Classification classification = classificationDAO.getById(ft.getClassificationId());
                if (classification != null) {
                    if (classification.getName().equals("--")) {
                        author.addAll(classificationDAO.getAll().
                                stream().map(Classification::getId).collect(Collectors.toList()));
                    }
                }

                author.add(ft.getClassificationId());
                lt.setClassificationId(author);
            }

            if (ft.getDocumentType() != null ) {
                Set<Long> author = new HashSet<>();

                DocumentType documentType = documentTypeDAO.getById(ft.getDocumentType());
                if (documentType != null) {
                    if (documentType.getName().equals("--")) {
                        author.addAll(documentTypeDAO.getAll().stream().map(DocumentType::getId).collect(Collectors.toList()));
                    }
                }

                author.add(ft.getDocumentType());
                lt.setDocumentType(author);
            }

            if (ft.getGroupId() != null) {
                Set<Long> author = new HashSet<>();

                ResearchGroup group = researchGroupDAO.getById(ft.getGroupId());
                if (group != null) {
                    if (group.getName().equals("--")) {
                        author.addAll(researchGroupDAO.getAll().stream().map(ResearchGroup::getId).collect(Collectors.toList()));
                    }
                }

                author.add(ft.getGroupId());
                lt.setGroupId(author);
            }

            if (ft.getKeyword() != null && !ft.getKeyword().equals("") ) {
//                Set<String> author = new HashSet<>();
//                author.add(ft.getKeyword());
                lt.setKeyword(ft.getKeyword());
            }

            if (ft.getMajorId() != null) {
                Set<Long> author = new HashSet<>();

                Major group = majorDAO.getById(ft.getMajorId());
                if (group != null) {
                    if (group.getName().equals("--") && group.getLevel() == 0) {
                        MajorFilter majorFilter = new MajorFilter();
                        majorFilter.setLevel(0L);
                        author.addAll(majorDAO.getPageOfData(majorFilter, null).stream().map(Major::getId).collect(Collectors.toList()));
                    }
                    if (group.getName().equals("--") && group.getLevel() == 1) {
                        MajorFilter majorFilter = new MajorFilter();
                        majorFilter.setLevel(1L);
                        majorFilter.setParentId(group.getParentId());
                        author.addAll(majorDAO.getPageOfData(majorFilter, null).stream().map(Major::getId).collect(Collectors.toList()));
                    }
                }

                author.add(ft.getMajorId());
                lt.setMajorId(author);
            }

            if (ft.getSpecializationId() != null) {
                Set<Long> author = new HashSet<>();

                Major group = majorDAO.getById(ft.getSpecializationId());
                if (group != null) {
                    if (group.getName().equals("--") && group.getLevel() == 0) {
                        MajorFilter majorFilter = new MajorFilter();
                        majorFilter.setLevel(0L);
                        author.addAll(majorDAO.getPageOfData(majorFilter, null).stream().map(Major::getId).collect(Collectors.toList()));
                    }
                    if (group.getName().equals("--") && group.getLevel() == 1) {
                        Major group1 = majorDAO.getById(group.getParentId());
                        if (!group1.getName().equals("--")) {
                            MajorFilter majorFilter = new MajorFilter();
                            majorFilter.setLevel(1L);
                            majorFilter.setParentId(group.getParentId());
                            author.addAll(majorDAO.getPageOfData(majorFilter, null).stream().map(Major::getId).collect(Collectors.toList()));
                        }
                    }
                }

                if (group.getName().equals("--") && group.getLevel() == 1) {
                    Major group1 = majorDAO.getById(group.getParentId());
                    if (!group1.getName().equals("--")) {
                        author.add(ft.getSpecializationId());
                        lt.setSpecializationId(author);
                    }
                } else {
                    author.add(ft.getSpecializationId());
                    lt.setSpecializationId(author);
                }
            }

            lt.setEndtime(ft.getEndtime());
            lt.setStarttime(ft.getStarttime());

            return super.getPageOfData(lt, pageinfo);

        }
        return null;
    }

    private Attachment saveAttachmentFullText(MultipartFile file, Document document) throws Exception {
//         create date directory
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
                new FileOutputStream(new File(reportDir + "/" + file.getName() + "." + type)));
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

            ss = ss.substring(1);
            report.setKeyword(ss);
        }

        report = documentDAO.create(report, callerId);

        DocumentReplica documentReplica = new DocumentReplica(report);
        if (report.getMajorId() != null) {
            Major major = majorDAO.getById(report.getMajorId());
            documentReplica.setChuyennganhId(report.getMajorId());
            if (major.getParentId() != null && major.getParentId() != 0) {
                documentReplica.setNganhId(major.getParentId());
            }
        }

        documentReplicaDAO.create(documentReplica, 0L);

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
                documentMember.setAuthorId(id);
                documentMember.setDocumentId(report.getId());
                documentMemberDAO.create(documentMember, report.getCreator());

                DocumentMemberReplica documentMemberReplica = new DocumentMemberReplica();
                documentMemberReplica.setAuthorId(id);
                documentMemberReplica.setDocumentId(report.getId());
                Author author = authorDAO.getById(id);
                if (author != null)
                    documentMemberReplica.setAffilicationId(author.getAffiliationId());
                documentMemberReplicaDAO.create(documentMemberReplica, 0L);
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

        if (object.getAttachmentsFullText() != null) {
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

        if (object.getAttachmentsFullText() != null) {
            Set<Attachment> attachmentsAdd = new HashSet<>();
            MultipartFile file = object.getAttachmentsFullText();
            try {
                attachmentsAdd.add(saveAttachmentFullText(file, document));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (object.getAuthors() != null && !object.getAuthors().isEmpty()) {
            for (Long id : object.getAuthors()) {
                DocumentMember documentMember = new DocumentMember();
                documentMember.setAuthorId(id);
                documentMember.setDocumentId(document.getId());
                documentMemberDAO.create(documentMember, document.getCreator());
            }
        }

        object.apply(document);
        return convert(getDAO().update(document, callerId));
    }

    @Override
    public void updatedb(){
        List<Document> documents = documentDAO.getAll();

        for (Document document : documents){
            String str = document.getPublishDate();
            String[] arrOfStr = str.split("-");
            if (arrOfStr.length >= 2){
                if (arrOfStr[1].length() < 2){
                    arrOfStr[1] = "0" + arrOfStr[1];
                    String s = "";
                    for (String ss : arrOfStr){
                        s = s + ss + "-";
                    }
                    document.setPublishDate(s.substring(0, s.length()-1));
                    documentDAO.update(document, 0L);
                }
            }
        }
    }


}
