package vn.com.mta.science.module.service.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.filter.BaseFilter;

@Getter
@Setter
@NoArgsConstructor
public class DocumentMemberFilter implements BaseFilter {
    private Long documentId;
}
