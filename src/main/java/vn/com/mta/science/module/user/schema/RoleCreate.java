package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaCreate;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class RoleCreate extends SchemaCreate<Role, String> {

    @NotNull
    private String name;

    private String description;

    @NotNull
    @NotEmpty
    private Set<String> authorities;

    @Override
    public Role toEntry() {
        Role role = new Role();
        role.setId(getId());
        role.setName(name);
        role.setDescription(description);
        role.setAuthorities(CollectionUtils.toString(authorities));
        role.setIsDefault(false);
        return role;
    }
}
