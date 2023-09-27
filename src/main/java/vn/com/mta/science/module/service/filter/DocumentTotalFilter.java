package vn.com.mta.science.module.service.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.filter.BaseFilter;

@Getter
@Setter
@NoArgsConstructor
public class DocumentTotalFilter implements BaseFilter {
    private Long documentType;

    private Long groupId;

    private Long majorId;

    private Long specializationId;

    private String keyword;

    private Long classificationId;

    private String authorId;

    private String starttime;

    private String endtime;
}
