package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Affiliation;

@Getter
@Setter
@NoArgsConstructor
public class AffiliationCreate extends GeneratedIDSchemaCreate<Affiliation> {

    private String name;

    private String description;

    private String contactPhone;

    private String contactEmail;

    private String country;

    private Integer level;

    private Long parentId;

    @Override
    public Affiliation toEntry() {
        Affiliation object = new Affiliation();
        object.setContactEmail(contactEmail);
        object.setContactPhone(contactPhone);
        object.setCountry(country);
        object.setLevel(level);
        object.setParentId(parentId);
        object.setName(name);
        object.setDescription(description);

        return object;
    }
}
