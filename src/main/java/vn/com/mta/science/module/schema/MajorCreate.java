package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Major;

@Getter
@Setter
@NoArgsConstructor
public class MajorCreate extends GeneratedIDSchemaCreate<Major> {

    private String name;

    private String description;

    private Integer level;

    private Integer parentId;

    @Override
    public Major toEntry() {
        Major object = new Major();
        object.setLevel(level);
        object.setParentId(parentId);
        object.setName(name);
        object.setDescription(description);

        return object;
    }
}
