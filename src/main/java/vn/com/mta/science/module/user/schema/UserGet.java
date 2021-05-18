package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserGet extends SchemaGet<User, Long> {

    private String fullName;

    private String username;

    private Set<RoleGet> roles;

    private String email;

    private Long affiliationId;

    private String affiliationName;

    private Long affiliationParentId;

    private String affiliationParentName;

//    private Long groupid;
//
//    private String groupName;

    public UserGet(User user) {
        super(user);
    }

    @Override
    public void parse(User user) {

        setFullName(user.getFullName());

        setEmail(user.getEmail());
        setUsername(user.getUsername());
        if (user.getAffiliation() != null) {
            setAffiliationId(user.getAffiliation().getId());
            setAffiliationName(user.getAffiliation().getName());
        }

        roles = new HashSet<>();
        if (user.getRoles() != null)
            for (Role role : user.getRoles()) {
                RoleGet roleGet = new RoleGet(role);
                roleGet.setAuthorities(CollectionUtils.toSet(role.getAuthorities()));
                roles.add(roleGet);
            }
    }
}
