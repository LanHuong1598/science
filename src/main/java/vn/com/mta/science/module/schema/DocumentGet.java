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

    private String sourceId;

    private String doi;

    private String publisher;

    private String publicationIndex;

    private String publishDate;

    private Long languageId;

    private String languageName;

    private String title;

    private String abstractText;

    private Long classificationId;

    private String classificationName;

    private Long documentType;

    private String documentTypeName;

    private Long groupId;

    private String groupName;

    private Long majorId;

    private String majorName;

   // private Long parentMajorId;

    private Long specializationId;

    private String specializationName;

    private Long citedNumber;

    private Collection<String> keyword;

    private String link;

    private Long mtaJournalId;

    private String mtaJournalName;

    private AttachmentGet attachmentsFullText;

    private String url;

    private Collection<AttachmentGet> attachmentsAbstract;

    private Collection<Long> authors;

    private Collection<String> authorsName;

    private Collection<CitedGet> citeds;

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
        this.setLink(document.getLink());
    }
}
