package vn.com.mta.science.module.service.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.filter.BaseFilter;

@Getter
@Setter
@NoArgsConstructor
public class StatsFilter implements BaseFilter {
    private String starttime;

    private String endtime;

    private String type ;

    private String keyword;

}
