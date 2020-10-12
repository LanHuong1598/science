package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.VoidableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document_replica")
public class DocumentReplica extends VoidableGeneratedIDEntry {

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
    private Long mtaJournalId;

    @Column(name = "cap0_id")
    private Long cap0Id;

    @Column(name = "cap1_id")
    private Long cap1Id;

    @Column(name = "cap2_id")
    private Long cap2Id;

    @Column(name = "nganh_id")
    private Long nganhId;

    @Column(name = "chuyennganh_id")
    private Long chuyennganhId;

    public DocumentReplica(Document document) {
        DocumentReplica documentReplica = new DocumentReplica();
        documentReplica.setDescription(document.getDescription());
        documentReplica.setAbstractText(document.getAbstractText());
        documentReplica.setCitedNumber(document.getCitedNumber());
        documentReplica.setClassificationId(document.getClassificationId());
        documentReplica.setDocumentType(document.getDocumentType());
        documentReplica.setDoi(document.getDoi());
        documentReplica.setGroupId(document.getGroupId());
        documentReplica.setLanguageId(document.getLanguageId());
        documentReplica.setMajorId(document.getMajorId());
        documentReplica.setMtaJournalId(document.getMtaJournalId());
        documentReplica.setPublicationIndex(document.getPublicationIndex());
        documentReplica.setPublishDate(document.getPublishDate());
        documentReplica.setPublisher(document.getPublisher());
        documentReplica.setSourceId(document.getSourceId());
        documentReplica.setSpecializationId(document.getSpecializationId());
        documentReplica.setTitle(document.getTitle());
    }
}
