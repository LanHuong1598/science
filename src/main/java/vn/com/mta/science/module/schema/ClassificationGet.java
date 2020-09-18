package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Classification;

@Getter
@Setter
@NoArgsConstructor
public class ClassificationGet extends SchemaGet<Classification, Long> {

    private String name;

    private String description;

    public ClassificationGet(Classification group) {
        super(group);
    }

    @Override
    public void parse(Classification group) {
        this.name = group.getName();
        this.description = group.getDescription();
    }
}
