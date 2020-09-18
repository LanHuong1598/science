package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.ResearchGroup;

@Getter
@Setter
@NoArgsConstructor
public class GroupGet extends SchemaGet<ResearchGroup, Long> {

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

    public GroupGet(ResearchGroup group) {
        super(group);
    }

    @Override
    public void parse(ResearchGroup group) {
        this.name = group.getName();
        this.contactEmail = group.getContactEmail();
        this.contactPhone = group.getContactPhone();
        this.description = group.getDescription();
        this.inventionNumber = group.getInventionNumber();
        this.isiNumber= group.getIsiNumber();
        this.nationalNumber = group.getNationalNumber();
        this.otherNumber = group.getOtherNumber();
        this.researchField = group.getResearchField();
        this.scopusNumber = group.getScopusNumber();
        this.usefulSolutionNumber = group.getUsefulSolutionNumber();
    }
}
