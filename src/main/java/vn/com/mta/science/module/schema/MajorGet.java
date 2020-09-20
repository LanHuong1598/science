package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Major;

@Getter
@Setter
@NoArgsConstructor
public class MajorGet extends SchemaGet<Major, Long> {

    private String name;

    private String description;

    private Integer level;

    private Integer parentId;

    public MajorGet(Major object) {
        super(object);
    }

    @Override
    public void parse(Major affiliation) {
        this.setParentId(affiliation.getParentId());
        this.setLevel(affiliation.getLevel());
        this.setName(affiliation.getName());
        this.setDescription(affiliation.getDescription());
    }
}
