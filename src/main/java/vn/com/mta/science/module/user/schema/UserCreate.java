package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.user.auth.ItechUserPasswordEncoder;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class UserCreate extends GeneratedIDSchemaCreate<User> {

    private String password;

    @NotNull
    private String fullname;

    @NotNull
    private String username;

    @NotNull
    @NotEmpty
    private Set<String> roleIds;

    private Long affiliationId;

    private Long groupId;

    @Override
    public User toEntry() {
        User user = new User();

        user.setUsername(username);
        user.setFullName(fullname);
        user.setPassword(ItechUserPasswordEncoder.getInstance().encode(password));

        user.setHidden(false);
        user.setEnabled(true);
        user.setAffiliationId(affiliationId);
        if (groupId != null) user.setGroupId(groupId);

        if(roleIds != null && !roleIds.isEmpty()) {
            user.setRoles(new HashSet<>());
            for (String roleId : roleIds) user.getRoles().add(new Role(roleId));
        }

        return user;
    }
}
