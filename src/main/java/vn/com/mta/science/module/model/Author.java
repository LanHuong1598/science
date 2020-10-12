package vn.com.mta.science.module.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.VoidableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
public class Author extends VoidableGeneratedIDEntry {
    @Column(name = "fullname")
    private String fullname;

    @Column(name = "degree_id")
    private Long degreeId;

    @Column(name = "academic_rank_id")
    private Long academicRankId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "scientific_title")
    private String scientificTitle;

    @Column(name = "affiliation_id")
    private Long affiliationId;

    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "orcid_id")
    private String orcidId;

    @Column(name = "link_google_scholar")
    private String linkGoogleScholar;

    @Column(name = "link_research_gate")
    private String linkResearchGate;

    @Column(name = "depth_research")
    private String depthResearch;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "isi_number")
    private Integer isiNumber;

    @Column(name = "scopus_number")
    private  Integer scopusNumber;

    @Column(name = "other_number")
    private Integer otherNumber;

    @Column(name = "national_number")
    private  Integer nationalNumber;

    @Column(name = "invention_number")
    private Integer inventionNumber;

    @Column(name = "useful_solution_number")
    private Integer usefulSolutionNumber;

    @Column(name = "is_mta")
    private Boolean isMTA;

    @Column(name = "verified")
    private Boolean verified;

}
