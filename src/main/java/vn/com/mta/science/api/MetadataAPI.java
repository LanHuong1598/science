package vn.com.mta.science.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.method.GeneratedIDSchemaAPIMethod;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.*;
import vn.com.mta.science.module.service.filter.AffiliationFilter;
import vn.com.mta.science.module.service.filter.DegreeFilter;
import vn.com.mta.science.module.service.filter.MajorFilter;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/secured/ws/rest/v1/")
public class MetadataAPI {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AffiliationService affiliationService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<AffiliationGet, Affiliation> affiliationMethods;

    @Bean
    @DependsOn({"affiliationService"})
    public GeneratedIDSchemaAPIMethod<AffiliationGet, Affiliation> getAffiliationMethods() {
        return new GeneratedIDSchemaAPIMethod<>(logger, affiliationService);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).AFFILIATION_GET)")
    @GetMapping("/affiliation")
    public ResponseEntity<APIResponse<List<AffiliationGet>>> getAffiliation() {
        return affiliationMethods.getList("id", 0, 1000, false);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).AFFILIATION_GET)")
    @GetMapping("/affiliation/{id}")
    public ResponseEntity<APIResponse<AffiliationGet>> getDocumentById(@PathVariable(name = "id") Long id) {
        return affiliationMethods.getById(id);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).AFFILIATION_GET)")
    @PostMapping("/search/affiliation")
    public ResponseEntity<APIResponse<List<AffiliationGet>>> search(
            @Valid @RequestBody AffiliationFilter object) {
        return affiliationMethods.search("id", 0, 1000,  false, object);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).AFFILIATION_MANAGE)")
    @PostMapping("/affiliation")
    public ResponseEntity<APIResponse<AffiliationGet>> create(
            @Valid @RequestBody AffiliationCreate object) {
        return affiliationMethods.create(object, 0L);
    }


    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<ClassificationGet, Classification> classificationMethods;

    @Bean
    @DependsOn({"classificationService"})
    public GeneratedIDSchemaAPIMethod<ClassificationGet, Classification> getClassificationMethods() {
        return new GeneratedIDSchemaAPIMethod<>(logger, classificationService);
    }


    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).CLASSIFICATION_GET)")
    @GetMapping("/classification")
    public ResponseEntity<APIResponse<List<ClassificationGet>>> getAllClassificationGet() {
        return classificationMethods.getList("id", 0, 1000, true);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).CLASSIFICATION_MANAGE)")
    @PostMapping("/classification")
    public ResponseEntity<APIResponse<ClassificationGet>> create(
            @Valid @RequestBody ClassificationCreate object) {
        return classificationMethods.create(object, 0L);
    }

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<DocumentTypeGet, DocumentType> documentTypeMethods;

    @Bean
    @DependsOn({"documentTypeService"})
    public GeneratedIDSchemaAPIMethod<DocumentTypeGet, DocumentType> getDocumentTypeMethods() {
        return new GeneratedIDSchemaAPIMethod<>(logger, documentTypeService);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DOCUMENTTYPE_GET)")
    @GetMapping("/document-type")
    public ResponseEntity<APIResponse<List<DocumentTypeGet>>> getAllDocumentGet() {
        return documentTypeMethods.getList(null, 0, 0, true);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DOCUMENTTYPE_MANAGE)")
    @PostMapping("/document-type")
    public ResponseEntity<APIResponse<DocumentTypeGet>> create(
            @Valid @RequestBody DocumentTypeCreate object) {
        return documentTypeMethods.create(object, 0L);
    }

    @Autowired
    private LanguageService languageService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<LanguageGet, Language> languageMethods;

    @Bean
    @DependsOn({"languageService"})
    public GeneratedIDSchemaAPIMethod<LanguageGet, Language> getLanguageMethods() {
        return new GeneratedIDSchemaAPIMethod<>(logger, languageService);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).LANGUAGE_GET)")
    @GetMapping("/language")
    public ResponseEntity<APIResponse<List<LanguageGet>>> getAllLanguageGet() {
        return languageMethods.getList("id", 0, 1000, true);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).LANGUAGE_MANAGE)")
    @PostMapping("/language")
    public ResponseEntity<APIResponse<LanguageGet>> create(
            @Valid @RequestBody LanguageCreate object) {
        return languageMethods.create(object, 0L);
    }

    @Autowired
    private DegreeService degreeService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<DegreeGet, Degree> degreeMethod;

    @Bean
    @DependsOn({"degreeService"})
    public GeneratedIDSchemaAPIMethod<DegreeGet, Degree> getDegreeMethod() {
        return new GeneratedIDSchemaAPIMethod<>(logger, degreeService);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DEGREE_GET)")
    @PostMapping("/search/degree")
    public ResponseEntity<APIResponse<List<DegreeGet>>> search(
            @Valid @RequestBody DegreeFilter object) {
        return degreeMethod.search("id", 0, 1000,  true, object);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DEGREE_MANAGE)")
    @PostMapping("/degree")
    public ResponseEntity<APIResponse<DegreeGet>> create(
            @Valid @RequestBody DegreeCreate object) {
        return degreeMethod.create(object, 0L);
    }

    @Autowired
    private MajorService majorService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<MajorGet, Major> majorMethod;

    @Bean
    @DependsOn({"majorService"})
    public GeneratedIDSchemaAPIMethod<MajorGet, Major> getMajorMethod() {
        return new GeneratedIDSchemaAPIMethod<>(logger, majorService);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).MAJOR_GET)")
    @GetMapping("/major")
    public ResponseEntity<APIResponse<List<MajorGet>>> getAllMajor() {
        return majorMethod.getList("id", 0, 1000, false);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).MAJOR_GET)")
    @GetMapping("/major/{id}")
    public ResponseEntity<APIResponse<MajorGet>> getMajor(@PathVariable(name = "id") Long id) {
        return majorMethod.getById(id);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).MAJOR_GET)")
    @PostMapping("/search/major")
    public ResponseEntity<APIResponse<List<MajorGet>>> searchMajor(
            @Valid @RequestBody MajorFilter object) {
        return majorMethod.search("id", 0, 1000, false, object);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).MAJOR_MANAGE)")
    @PostMapping("/major")
    public ResponseEntity<APIResponse<MajorGet>> create(
            @Valid @RequestBody MajorCreate object) {
        return majorMethod.create(object, 0L);
    }
    @Autowired
    private CitedService citedService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<CitedGet, Cited> citedMethods;

    @Bean
    @DependsOn({"citedService"})
    public GeneratedIDSchemaAPIMethod<CitedGet, Cited> getCitedGeneratedIDSchemaAPIMethod() {
        return new GeneratedIDSchemaAPIMethod<>(logger, citedService);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).CITED_MANAGE)")
    @PostMapping("/cited")
    public ResponseEntity<APIResponse<CitedGet>> createCited
            (@Valid @RequestBody CitedCreate citedCreate) {
        return citedMethods.create(citedCreate, 0L);
    }


}
