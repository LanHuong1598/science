package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.GeneratedIDSchemaCreate;
import vn.com.mta.science.module.model.Author;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthorCreate extends GeneratedIDSchemaCreate<Author> {

    private String fullname;

    private Long degreeId;

    private Long academicRankId;

    private String gender;

    private Date birthdate;

    private String scientificTitle;

    private Long affiliationId;

    private Long majorId;

    private String orcidId;

    private String linkGoogleScholar;

    private String linkResearchGate;

    private String phone;

    private String depthResearch;

    private Integer isiNumber;

    private  Integer scopusNumber;

    private Integer otherNumber;

    private  Integer nationalNumber;

    private Integer inventionNumber;

    private Integer usefulSolutionNumber;

    private Boolean isMTA;

    private Boolean verified;

    private List<String> email;

    private List<Long> groupIds;

    @Override
    public Author toEntry() {
        Author object = new Author();
        object.setAcademicRankId(academicRankId);
        object.setAffiliationId(affiliationId);
        object.setBirthdate(birthdate);
        object.setDegreeId(degreeId);
        object.setFullname(fullname);
        object.setGender(gender);
        object.setInventionNumber(inventionNumber);
        object.setIsiNumber(isiNumber);
        object.setLinkGoogleScholar(linkGoogleScholar);
        object.setLinkResearchGate(linkResearchGate);
        object.setMajorId(majorId);
        object.setNationalNumber(nationalNumber);
        object.setOrcidId(orcidId);
        object.setOtherNumber(otherNumber);
        object.setScientificTitle(scientificTitle);
        object.setScopusNumber(scopusNumber);
        object.setUsefulSolutionNumber(usefulSolutionNumber);
        object.setPhone(phone);
        object.setDepthResearch(depthResearch);
        object.setIsMTA(isMTA);
        object.setVerified(verified);
        return object;
    }
}
