package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "language")
@Setter
@Getter
public class Language extends AuditableGeneratedIDEntry {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
