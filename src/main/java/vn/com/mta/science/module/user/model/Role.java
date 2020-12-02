package vn.com.mta.science.module.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.AuditableDbEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Getter @Setter @NoArgsConstructor
public class Role extends AuditableDbEntry<String> {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "is_default")
    private Boolean isDefault;

    public Role(String id) {setId(id);}

}
