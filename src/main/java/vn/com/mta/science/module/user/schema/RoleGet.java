package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.util.CollectionUtils;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class RoleGet extends SchemaGet<Role, String> {

    private String name;

    private String description;

    private Set<String> authorities;

    private Boolean isDefault;

    public RoleGet(Role role) {
        super(role);
    }

    @Override
    public void parse(Role role) {
        name = role.getName();
        description = role.getDescription();
        authorities = CollectionUtils.toSet(role.getAuthorities());
        isDefault = role.getIsDefault();
    }
}
