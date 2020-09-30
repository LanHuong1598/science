package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "invention_member")
@Getter
@Setter
@NoArgsConstructor
public class InventionMember extends AuditableGeneratedIDEntry {
    @Column(name = "invention_id", nullable = false)
    private Long invention_id;

    @Column(name = "author_id")
    private Long author_id;
}
