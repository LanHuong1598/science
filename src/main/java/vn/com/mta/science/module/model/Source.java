package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.VoidableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "source")
@Getter
@Setter
public class Source extends VoidableGeneratedIDEntry {
    @Column(name = "cite_score")
    private Float citeScore;

    @Column(name = "sjr")
    private Float sjr;

    @Column(name = "snip")
    private Float snip;

    @Column(name = "issn")
    private String issn;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "subject_area")
    private String subjectArea;


}
