package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Affiliation;

@Getter
@Setter
@NoArgsConstructor
public class AffiliationGet extends SchemaGet<Affiliation, Long> {

    private String name;

    private String description;

    private String contactPhone;

    private String contactEmail;

    private String country;

    private Integer level;

    private Long parentId;

    public AffiliationGet(Affiliation object) {
        super(object);
    }

    @Override
    public void parse(Affiliation affiliation) {
        this.setParentId(affiliation.getParentId());
        this.setLevel(affiliation.getLevel());
        this.setCountry(affiliation.getCountry());
        this.setContactPhone(affiliation.getContactPhone());
        this.setContactEmail(affiliation.getContactEmail());
        this.setName(affiliation.getName());
        this.setDescription(affiliation.getDescription());
    }
}
