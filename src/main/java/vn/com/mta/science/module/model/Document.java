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
@Table(name = "document")
public class Document extends VoidableGeneratedIDEntry {

    @Column(name = "description")
    private String description;

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "doi")
    private String doi;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_index")
    private String publicationIndex;

    @Column(name = "publishDate")
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
    private String citedNumber;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "mta_jounal_id")
    private String mtaJournalId;
}
