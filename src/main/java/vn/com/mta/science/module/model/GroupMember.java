package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "group_member")
@Setter
@Getter
public class GroupMember extends AuditableGeneratedIDEntry {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "author_id")
    private  Long authorId;


}
