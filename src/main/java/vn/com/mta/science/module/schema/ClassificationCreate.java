package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Classification;

@Getter
@Setter
@NoArgsConstructor
public class ClassificationCreate extends GeneratedIDSchemaCreate<Classification> {

    private String name;

    private String description;

    @Override
    public Classification toEntry() {
        Classification newGroup = new Classification();
        newGroup.setName(name);
        newGroup.setDescription(description);
        return newGroup;
    }
}
