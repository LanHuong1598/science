package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.repository.service.detail.schema.SchemaUpdate;
import vn.com.mta.science.module.model.Author;

import java.util.Date;
import java.util.List;

@SuppressWarnings("Duplicates")
@Getter
@Setter
@NoArgsConstructor
public class AuthorUpdate extends SchemaUpdate<Author, Long> {

    private String fullname;

    private Long degreeId;

    private Long academicRankId;

    private String gender;

    private Date birthdate;

    private String scientificTitle;

    private Long affiliationId;

    private Long majorId;

    private String  orcidId;

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

    private List<String> email;

    private List<Long> groupIds;

    private Boolean isMTA;

    private Boolean verified;

    @Override
    public boolean apply(Author object) {
        boolean modified = false;

        if (fullname != null) {
            object.setFullname(fullname);
            modified = true;
        }
        if (degreeId != null) {
            object.setDegreeId(degreeId);
            modified = true;
        }
        if (academicRankId != null) {
            object.setAcademicRankId(academicRankId);
            modified = true;
        }
        if (gender != null) {
            object.setGender(gender);
            modified = true;
        }
        if (birthdate != null) {
            object.setBirthdate(birthdate);
            modified = true;
        }
        if (scientificTitle != null) {
            object.setScientificTitle(scientificTitle);
            modified = true;
        }
        if (affiliationId != null) {
            object.setAffiliationId(affiliationId);
            modified = true;
        }
        if (majorId != null) {
            object.setMajorId(majorId);
            modified = true;
        }
        if (orcidId != null) {
            object.setOrcidId(orcidId);
            modified = true;
        }
        if (linkGoogleScholar != null) {
            object.setLinkGoogleScholar(linkGoogleScholar);
            modified = true;
        }
        if (linkResearchGate != null) {
            object.setLinkResearchGate(linkResearchGate);
            modified = true;
        }
        if (phone != null) {
            object.setPhone(phone);
            modified = true;
        }
        if (depthResearch != null) {
            object.setDepthResearch(depthResearch);
            modified = true;
        }

        if (isMTA != null) {
            object.setIsMTA(isMTA);
            modified = true;
        }

        if (verified != null) {
            object.setVerified(verified);
            modified = true;
        }
        return modified;
    }
}
