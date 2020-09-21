package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Document;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class DocumentGet extends SchemaGet<Document, Long> {

    private String description;

    private Long sourceId;

    private String doi;

    private String publisher;

    private Integer publicationIndex;

    private String publishDate;

    private Long languageId;

    private String title;

    private String abstractText;

    private Long classificationId;

    private Long documentType;

    private Long groupId;

    private Long majorId;

    private Long specializationId;

    private Integer citedNumber;

    private Collection<String> keyword;

    private Integer mtaJournalId;

    private Collection<AttachmentGet> attachmentsFullText;

    private Collection<AttachmentGet> attachmentsAbstract;

    private Collection<Long> authors;

    public DocumentGet(Document document) {
        super(document);
    }

    @Override
    public void parse(Document document) {
        this.setDescription(document.getDescription());
        this.setAbstractText(document.getAbstractText());
        this.setCitedNumber(document.getCitedNumber());
        this.setClassificationId(document.getClassificationId());
        this.setDocumentType(document.getDocumentType());
        this.setDoi(document.getDoi());
        this.setGroupId(document.getGroupId());
        this.setLanguageId(document.getLanguageId());
        this.setMajorId(document.getMajorId());
        this.setMtaJournalId(document.getMtaJournalId());
        this.setPublicationIndex(document.getPublicationIndex());
        this.setPublishDate(document.getPublishDate());
        this.setPublisher(document.getPublisher());
        this.setSourceId(document.getSourceId());
        this.setSpecializationId(document.getSpecializationId());
        this.setTitle(document.getTitle());
    }
}
