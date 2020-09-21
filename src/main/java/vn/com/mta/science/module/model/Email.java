package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "email")
@Setter
@Getter

public class Email extends AuditableGeneratedIDEntry {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "author_id")
    private  Long authorId;
}
