package vn.com.mta.science.module.user.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.filter.BaseFilter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class UserFilter implements BaseFilter {

    @Size(min = 3)
    private String fullName;

    private String departmentId;

    private String email;

    private String phoneNumber;

    private Set<String> roleIds;

    private Boolean hidden;
}
