package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.VoidableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "research_group")
@Getter
@Setter
@NoArgsConstructor

public class ResearchGroup extends VoidableGeneratedIDEntry {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "research_field")
    private String researchField;

    @Column(name = "isi_number")
    private Integer isiNumber;

    @Column(name = "scopus_number")
    private Integer scopusNumber;

    @Column(name = "other_number")
    private Integer otherNumber;

    @Column(name = "national_number")
    private Integer nationalNumber;

    @Column(name = "invention_number")
    private Integer inventionNumber;

    @Column(name = "useful_solution_number")
    private Integer usefulSolutionNumber;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

}
