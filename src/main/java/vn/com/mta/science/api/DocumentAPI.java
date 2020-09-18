package vn.com.mta.science.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.method.GeneratedIDSchemaAPIMethod;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.schema.DocumentCreate;
import vn.com.mta.science.module.schema.DocumentGet;
import vn.com.mta.science.module.service.DocumentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/secured/ws/rest/v1/")
public class DocumentAPI {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DocumentService documentService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<DocumentGet, Document> documentMethods;

    @Bean
    @DependsOn({"documentService"})
    public GeneratedIDSchemaAPIMethod<DocumentGet, Document> getDocumentMethods() {
        return new GeneratedIDSchemaAPIMethod<>(logger, documentService);
    }

    @GetMapping("/document")
    public ResponseEntity<APIResponse<List<DocumentGet>>> getAllDocument() {
        return documentMethods.getList(null, 0, 0, true);
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<APIResponse<DocumentGet>> getDocumentById(@PathVariable(name = "id") Long id) {
        return documentMethods.getById(id);
    }

    @PostMapping("/document")
    public ResponseEntity<APIResponse<DocumentGet>> createDocument(
            @Valid @ModelAttribute("uploadForm") DocumentCreate object) {
        return documentMethods.create(object, 0L);
    }
}
