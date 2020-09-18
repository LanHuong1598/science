package vn.com.mta.science.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.VoidableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "degree")
@Getter
@Setter
@NoArgsConstructor
public class Degree extends VoidableGeneratedIDEntry {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    // 1 hoc ham,. // 2 hoc vi
    @Column(name = "type")
    private Integer type;

}
