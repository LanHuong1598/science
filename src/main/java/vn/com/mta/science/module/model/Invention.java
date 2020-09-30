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

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "application_no", nullable = false)
    private String applicationNo;

    @Column(name = "filling_date")
    private String fillingDate;

    @Column(name = "decided_id")
    private String decidedId;

    @Column(name = "publication_no")
    private String publicationNo;

    @Column(name = "publication_date")
    private String publicationDate;

    @Column(name = "ptc_registration_date")
    private String ptcRegistrationDate;

    @Column(name = "ptc_expired_date")
    private String ptcExpiredDate;

    @Column(name = "ipc_id")
    private String ipcId;

    @Column(name = "source")
    private String source;

    @Column(name = "link")
    private String link;

    @Column(name = "owner")
    private String owner;

    @Column(name = "owner_address")
    private String ownerAddress;

    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "type")
    private String type;
}
