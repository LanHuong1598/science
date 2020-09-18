package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.ResearchGroup;

@Getter
@Setter
@NoArgsConstructor
public class GroupCreate extends GeneratedIDSchemaCreate<ResearchGroup> {

    private String name;

    private String description;

    private String researchField;

    private Integer isiNumber;

    private Integer scopusNumber;

    private Integer otherNumber;

    private Integer nationalNumber;

    private Integer inventionNumber;

    private Integer usefulSolutionNumber;

    private String contactPhone;

    private String contactEmail;

    @Override
    public ResearchGroup toEntry() {
        ResearchGroup newGroup = new ResearchGroup();
        newGroup.setName(this.name);
        newGroup.setDescription(this.description);
        newGroup.setContactEmail(this.contactEmail);
        newGroup.setResearchField(this.researchField);
        newGroup.setIsiNumber(this.isiNumber);
        newGroup.setScopusNumber(this.scopusNumber);
        newGroup.setOtherNumber(this.otherNumber);
        newGroup.setNationalNumber(this.nationalNumber);
        newGroup.setInventionNumber(this.inventionNumber);
        newGroup.setUsefulSolutionNumber(this.usefulSolutionNumber);
        newGroup.setContactPhone(this.contactPhone);
        return newGroup;
    }
}
