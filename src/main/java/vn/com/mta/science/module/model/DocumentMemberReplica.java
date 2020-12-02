package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "document_member_replica")
@Getter
@Setter
@NoArgsConstructor
public class DocumentMemberReplica extends AuditableGeneratedIDEntry {
    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "affiliation_id")
    private Long affilicationId;
}
