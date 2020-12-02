package vn.com.mta.science.module.user.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.filter.BaseFilter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class RoleFilter implements BaseFilter {

    private List<String> excludedIds;

    public RoleFilter(List<String> ids) {
        this.excludedIds = ids;
    }
}
