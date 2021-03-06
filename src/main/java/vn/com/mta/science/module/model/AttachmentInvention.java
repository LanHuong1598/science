package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "attachment_invention")
@Getter
@Setter
@NoArgsConstructor
public class AttachmentInvention extends AuditableGeneratedIDEntry {
    @Column(name = "invention_id", nullable = false)
    private Long invention_id;

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
