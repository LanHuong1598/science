package vn.com.mta.science.module.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.model.VoidableGeneratedIDEntry;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public class Person extends VoidableGeneratedIDEntry {

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "race")
    private String race;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birthdate")
    private Date birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "district")
    private String district;

    @Column(name = "city_province")
    private String city;

    @Column(name = "country")
    private String country;
}
