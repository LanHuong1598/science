package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;

import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdate extends SchemaUpdate<User, Long> {

    private Long affiliationId;

    private Long groupId;

    private Set<String> roleIds;

    private String fullname;


    @Override
    public boolean apply(User user) {
        boolean modified = false;

        if (affiliationId != null) {
            user.setAffiliationId(affiliationId);
            modified = true;
        }
        if (groupId != null) {
            user.setGroupId(groupId);
            modified = true;
        }

        if (fullname != null) {
            user.setFullName(fullname);
            modified = true;
        }

        if (roleIds != null && !roleIds.isEmpty()) {
            user.setRoles(new HashSet<>());
            for (String roleId : roleIds) user.getRoles().add(new Role(roleId));
            modified = true;
        }
        return modified;
    }
}
