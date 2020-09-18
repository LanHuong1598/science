package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Affiliation;
import vn.com.mta.science.module.model.Author;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthorGet extends SchemaGet<Author, Long> {

    private String fullname;

    private String degreeId;

    private Integer academicRankId;

    private String gender;

    private Date birthdate;

    private String scientificTitle;

    private Integer affiliationId;

    private Integer majorId;

    private Integer orcidId;

    private String linkGoogleScholar;

    private String linkResearchGate;

    private Integer isiNumber;

    private  Integer scopusNumber;

    private Integer otherNumber;

    private  Integer nationalNumber;

    private Integer inventionNumber;

    private Integer usefulSolutionNumber;

    private String phone;

    private String depthResearch;

    private String email;

    private List<Long> groupIds;

    public AuthorGet(Author object) {
        super(object);
    }

    @Override
    public void parse(Author object) {
        this.setAcademicRankId(object.getAcademicRankId());
        this.setAffiliationId(object.getAffiliationId());
        this.setBirthdate(object.getBirthdate());
        this.setDegreeId(object.getDegreeId());
        this.setFullname(object.getFullname());
        this.setGender(object.getGender());
        this.setInventionNumber(object.getInventionNumber());
        this.setIsiNumber(object.getIsiNumber());
        this.setLinkGoogleScholar(object.getLinkGoogleScholar());
        this.setLinkResearchGate(object.getLinkResearchGate());
        this.setMajorId(object.getMajorId());
        this.setNationalNumber(object.getNationalNumber());
        this.setOrcidId(object.getOrcidId());
        this.setOtherNumber(object.getOtherNumber());
        this.setScientificTitle(object.getScientificTitle());
        this.setScopusNumber(object.getScopusNumber());
        this.setUsefulSolutionNumber(object.getUsefulSolutionNumber());
        this.setPhone(object.getPhone());
        this.setDepthResearch(object.getDepthResearch());
        this.setEmail(object.getEmail());
    }
}
