package vn.com.mta.science.module.user.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.util.CollectionUtils;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class RoleUpdate extends SchemaUpdate<Role, String> {

    private String name;

    private String description;

    private Set<String> authorities;

    @Override
    public boolean apply(Role object) {
        boolean modified = false;

        if (getName() != null) {
            object.setName(getName());
            modified = true;
        }

        if (getDescription() != null) {
            object.setDescription(getDescription());
            modified = true;
        }

        if (getAuthorities() != null && !getAuthorities().isEmpty()) {
            object.setAuthorities(CollectionUtils.toString(getAuthorities()));
            modified = true;
        }

        return modified;
    }
}
