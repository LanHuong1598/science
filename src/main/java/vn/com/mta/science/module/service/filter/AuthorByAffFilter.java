package vn.com.mta.science.module.service.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.filter.BaseFilter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorByAffFilter implements BaseFilter {
    private Long cap0Id;

    private Long cap1Id;

    private Long cap2Id;
}
