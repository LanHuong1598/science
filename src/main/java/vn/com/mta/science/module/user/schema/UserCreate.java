package vn.com.mta.science.module.user.schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.user.auth.ItechUserPasswordEncoder;
import vn.com.mta.science.module.user.model.Gender;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class UserCreate extends GeneratedIDSchemaCreate<User> {

    private String nationality;

    private String race;

    @NotNull
    private String fullName;

    @NotNull
    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private Date birthDate;

    private String address;

    private String district;

    private String city;

    private String country;

    @NotNull
    private String password;

    @NotNull
    private String username;

    @NotNull
    @NotEmpty
    private Set<String> roleIds;

    private Boolean enabled;

    private String degree;

    private String title;

    private String phoneNumber;

    private String email;

    private String specialization;

    @Override
    public User toEntry() {
        User user = new User();
        user.setNationality(nationality);
        user.setRace(race);
        user.setFullName(fullName);
        user.setGender(gender);
        user.setBirthDate(birthDate);
        user.setAddress(address);
        user.setDistrict(district);
        user.setCity(city);
        user.setCountry(country);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setPassword(ItechUserPasswordEncoder.getInstance().encode(password));

        user.setDegree(degree);
        user.setTitle(title);
        user.setSpecialization(specialization);
        user.setHidden(false);

        if(enabled != null) user.setEnabled(enabled);
        else user.setEnabled(false);

        if(roleIds != null && !roleIds.isEmpty()) {
            user.setRoles(new HashSet<>());
            for (String roleId : roleIds) user.getRoles().add(new Role(roleId));
        }

        return user;
    }
}
