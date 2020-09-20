package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mta_journal_type")
@Getter
@Setter
@NoArgsConstructor
public class MtaJournalType extends AuditableGeneratedIDEntry {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
