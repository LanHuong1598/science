package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Classification;
import vn.com.mta.science.module.model.Degree;

@Getter
@Setter
@NoArgsConstructor
public class DegreeCreate extends GeneratedIDSchemaCreate<Degree> {

    private String name;

    private String description;

    private Integer type;

    @Override
    public Degree toEntry() {
        Degree newGroup = new Degree();
        newGroup.setName(name);
        newGroup.setDescription(description);
        newGroup.setType(type);
        return newGroup;
    }
}
