package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mta_journal")
@Getter
@Setter
@NoArgsConstructor
public class MtaJournal extends AuditableGeneratedIDEntry {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    @Column(name = "index")
    private String index;

    @Column(name = "publish_date")
    private String publish_date;

    @Column(name = "type")
    private String type;

}
