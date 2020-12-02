package vn.com.mta.science.module.user.schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;

import vn.com.mta.science.module.user.auth.ItechUserPasswordEncoder;
import vn.com.mta.science.module.user.model.Gender;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdate extends SchemaUpdate<User, Long> {

    private String nationality;

    private String race;

    private String fullName;

    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private Date birthDate;

    private String address;

    private String district;

    private String city;

    private String country;

    private String password;

    private Set<String> roleIds;

    private Boolean enabled;

    private String degree;

    private String title;

    private String phoneNumber;

    private String email;

    private String specialization;

    @Override
    public boolean apply(User user) {
        boolean modified = false;

        if(getRace() != null) {
            user.setRace(getRace());
            modified = true;
        }

        if(getFullName() != null) {
            user.setFullName(getFullName());
            modified = true;
        }

        if(getGender() != null) {
            user.setGender(getGender());
            modified = true;
        }

        if(getBirthDate() != null) {
            user.setBirthDate(getBirthDate());
            modified = true;
        }

        if(getAddress() != null) {
            user.setAddress(getAddress());
            modified = true;
        }

        if(getDistrict() != null) {
            user.setDistrict(getDistrict());
            modified = true;
        }

        if(getCity() != null) {
            user.setCity(getCity());
            modified = true;
        }

        if(getCountry() != null) {
            user.setCountry(getCountry());
            modified = true;
        }

        if(password != null) {
            user.setPassword(ItechUserPasswordEncoder.getInstance().encode(password));
            modified = true;
        }

        if(email != null) {
            user.setEmail(email);
            modified = true;
        }

        if(phoneNumber != null ) {
            user.setPhoneNumber(phoneNumber);
            modified = true;
        }

        if(enabled != null) {
            user.setEnabled(enabled);
            modified = true;
        }

        if(roleIds != null && !roleIds.isEmpty()) {
            user.setRoles(new HashSet<>());
            for (String roleId : roleIds) user.getRoles().add(new Role(roleId));
            modified = true;
        }

        if(degree != null) {
            user.setDegree(degree);
            modified = true;
        }

        if(title != null) {
            user.setTitle(title);
            modified = true;
        }

        if(specialization != null) {
            user.setSpecialization(specialization);
            modified = true;
        }

        return modified;
    }
}
