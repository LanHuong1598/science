package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
public class Attachment extends AuditableGeneratedIDEntry {
    @Column(name = "document_id", nullable = false)
    private Long document_id;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "content_size_kb")
    private Long contentSizeKb;

    @Column(name = "type")
    private Long type;

    @Column(name = "url")
    private String url;

    @Column(name = "doi")
    private String doi;

    @Column(name = "issn")
    private String issn;

}
