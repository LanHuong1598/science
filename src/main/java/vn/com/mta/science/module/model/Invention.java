package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.VoidableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "invention")
@Getter
@Setter
@NoArgsConstructor
public class Invention extends VoidableGeneratedIDEntry {

    @Column(name = "application_no", nullable = false)
    private Long application_no;

    @Column(name = "filling_date")
    private String filling_date;

    @Column(name = "publication_no")
    private String publication_no;

    @Column(name = "publication_date", nullable = false)
    private String publication_date;

    @Column(name = "ptc_registration_date", nullable = false)
    private String ptc_registration_date;

    @Column(name = "ptc_expired_date")
    private Long ptc_expired_date;

    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "type")
    private String type;
}
