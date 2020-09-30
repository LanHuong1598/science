package vn.com.mta.science.module.service.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.filter.BaseFilter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class AuthorFilter implements BaseFilter {
    private List<Long> ids;
}
