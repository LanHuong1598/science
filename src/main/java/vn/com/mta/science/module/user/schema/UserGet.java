package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.user.model.Gender;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserGet extends SchemaGet<User, Long> {

    private String nationality;

    private String race;

    private String fullName;

    private Gender gender;

    private String birthDate;

    private String address;

    private String district;

    private String city;

    private String country;

    private String username;

    private Set<RoleGet> roles;

    private Boolean enabled;

    private String degree;

    private String title;

    private String phoneNumber;

    private String email;

    private String departmentName;

    private String specialization;

    public UserGet(User user) {
        super(user);
    }

    @Override
    public void parse(User user) {
        setNationality(user.getNationality());
        setRace(user.getRace());
        setFullName(user.getFullName());
        setGender(user.getGender());
        if (user.getBirthDate() != null)
            setBirthDate( new SimpleDateFormat("yyyyMMdd").format(user.getBirthDate()));
        setAddress(user.getAddress());
        setDistrict(user.getDistrict());
        setCity(user.getCity());
        setCountry(user.getCountry());
        setEmail(user.getEmail());
        setPhoneNumber(user.getPhoneNumber());
        setUsername(user.getUsername());
        setEnabled(user.getEnabled());

        setDegree(user.getDegree());
        setTitle(user.getTitle());
        setSpecialization(user.getSpecialization());

        roles = new HashSet<>();
        for(Role role : user.getRoles()) {
            RoleGet roleGet = new RoleGet(role);
            roleGet.setAuthorities(CollectionUtils.toSet(role.getAuthorities()));
            roles.add(roleGet);
        }
    }
}
