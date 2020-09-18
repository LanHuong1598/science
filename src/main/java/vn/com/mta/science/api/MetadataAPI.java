package vn.com.mta.science.api;

import org.apache.commons.codec.language.bm.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.method.GeneratedIDSchemaAPIMethod;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.mta.science.module.model.*;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.*;
import vn.com.mta.science.module.service.filter.AffiliationFilter;
import vn.com.mta.science.module.service.filter.DegreeFilter;

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

    @GetMapping("/affiliation")
    public ResponseEntity<APIResponse<List<AffiliationGet>>> getAllDocument() {
        return affiliationMethods.getList(null, 0, 0, true);
    }

    @GetMapping("/affiliation/{id}")
    public ResponseEntity<APIResponse<AffiliationGet>> getDocumentById(@PathVariable(name = "id") Long id) {
        return affiliationMethods.getById(id);
    }

    @PostMapping("/search/affiliation")
    public ResponseEntity<APIResponse<List<AffiliationGet>>> search(
            @Valid @RequestBody AffiliationFilter object) {
        return affiliationMethods.search(null, 0, 0,  true, object);
    }

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


    @GetMapping("/classification")
    public ResponseEntity<APIResponse<List<ClassificationGet>>> getAllClassificationGet() {
        return classificationMethods.getList(null, 0, 0, true);
    }

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

    @GetMapping("/document-type")
    public ResponseEntity<APIResponse<List<DocumentTypeGet>>> getAllDocumentGet() {
        return documentTypeMethods.getList(null, 0, 0, true);
    }

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

    @GetMapping("/language")
    public ResponseEntity<APIResponse<List<LanguageGet>>> getAllLanguageGet() {
        return languageMethods.getList(null, 0, 0, true);
    }

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

    @PostMapping("/search/degree")
    public ResponseEntity<APIResponse<List<DegreeGet>>> search(
            @Valid @RequestBody DegreeFilter object) {
        return degreeMethod.search(null, 0, 0,  true, object);
    }

    @PostMapping("/degree")
    public ResponseEntity<APIResponse<DegreeGet>> create(
            @Valid @RequestBody DegreeCreate object) {
        return degreeMethod.create(object, 0L);
    }

}
