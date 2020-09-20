package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Degree;

@Getter
@Setter
@NoArgsConstructor
public class DegreeGet extends SchemaGet<Degree, Long> {

    private String name;

    private String description;

    private String type;

    public DegreeGet(Degree group) {
        super(group);
    }

    @Override
    public void parse(Degree object) {
        this.name = object.getName();
        this.description = object.getDescription();
        this.type = object.getType();
    }
}
