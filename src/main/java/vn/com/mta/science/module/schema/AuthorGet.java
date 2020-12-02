package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaGet;
import vn.com.mta.science.module.model.Author;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthorGet extends SchemaGet<Author, Long> {

    private String fullname;

    private Long degreeId;

    private Long academicRankId;

    private String gender;

    private String birthdate;

    private String scientificTitle;

    private Long affiliationId;

    private Long parentAffiliationId;

    private Long majorId;

    private Long parentMajorId;

    private String orcidId;

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

    private List<String> email;

    private List<Long> groupIds;

    private String donvi;

    private Boolean isMTA;

    private Boolean verified;

    public AuthorGet(Author object) {
        super(object);
    }

    @Override
    public void parse(Author object) {
        this.setAcademicRankId(object.getAcademicRankId());
        this.setAffiliationId(object.getAffiliationId());
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
        this.setIsMTA(object.getIsMTA());
        this.setVerified(object.getVerified());
    }
}
