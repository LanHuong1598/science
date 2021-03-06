package vn.com.mta.science.module.service.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.filter.BaseFilter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class InventionFilter implements BaseFilter {
    private String type ;

    private String starttime;

    private String endtime;

    private Set<Long> affiliationId;

    private Set<Long> groupId;

    private Set<Long> majorId;

    private Set<Long> specializationId;

    private String keyword;

    private Set<Long> classificationId;

    private Set<String> authorId;

}
