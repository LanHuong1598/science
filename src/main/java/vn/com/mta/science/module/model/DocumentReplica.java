package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableDbEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document_replica")
public class DocumentReplica extends AuditableDbEntry<Long> {

    @Column(name = "description")
    private String description;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "doi")
    private String doi;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_index")
    private String publicationIndex;

    @Column(name = "publish_date")
    private String publishDate;

    @Column(name = "language_id")
    private Long languageId;

    @Column(name = "title")
    private String title;

    @Column(name = "abstract_text")
    private String abstractText;

    @Column(name = "classification_id")
    private Long classificationId;

    @Column(name = "document_type")
    private Long documentType;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "specialization_id")
    private Long specializationId;

    @Column(name = "cited_number")
    private Long citedNumber;

    @Column(name = "keyword")
    private Long keyword;

    @Column(name = "mta_jounal_id")
    private String mtaJournalId;

    @Column(name = "nganh_id")
    private Long nganhId;

    @Column(name = "chuyennganh_id")
    private Long chuyennganhId;

    public DocumentReplica(Document document) {
        this.setId(document.getId());
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
