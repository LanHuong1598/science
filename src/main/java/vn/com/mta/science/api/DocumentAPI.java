package vn.com.mta.science.api;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
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
import vn.com.mta.science.module.model.Document;
import vn.com.mta.science.module.schema.*;
import vn.com.mta.science.module.service.DocumentService;
import vn.com.mta.science.module.service.filter.DocumentFilter;
import vn.com.mta.science.module.service.filter.DocumentGetFilter;
import vn.com.mta.science.module.service.filter.DocumentTotalFilter;

import javax.validation.Valid;
import java.util.Collections;
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

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DOCUMENT_GET)")
    @GetMapping("/document")
    public ResponseEntity<APIResponse<List<DocumentGet>>> getAllDocument(
            @RequestParam(required = false, name = "offset", defaultValue = "1") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit) {
        return documentMethods.getList("id", Math.max(offset - 1, 0), limit, false);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DOCUMENT_GET)")
    @GetMapping("/document/{id}")
    public ResponseEntity<APIResponse<DocumentGet>> getDocumentById(@PathVariable(name = "id") Long id) {
        return documentMethods.getById(id);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DOCUMENT_MANAGE)")
    @PostMapping("/document")
    public ResponseEntity<APIResponse<DocumentGet>> createDocument(
            @Valid @ModelAttribute("uploadForm") DocumentCreate object) {
        return documentMethods.create(object, 0L);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DOCUMENT_GET)")
    @PostMapping("/document/search")
    public ResponseEntity<APIResponse<List<DocumentGet>>> findDocument(
    @RequestParam(required = false, name = "documenttype", defaultValue = "12") Long documenttype,
    @RequestParam(required = false, name = "keyword", defaultValue = "") String keyword,
    @RequestParam(required = false, name = "offset", defaultValue = "1") int offset,
    @RequestParam(required = false, name = "limit", defaultValue = "10") int limit)
    {

        DocumentFilter ob = new DocumentFilter();
        ob.setDocumentType(Collections.singleton(documenttype));
        ob.setKeyword(keyword);
        return documentMethods.search("id", Math.max(offset - 1, 0), limit, false, ob);
    }


    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DOCUMENT_MANAGE)")
    @PutMapping("/document")
    public ResponseEntity<APIResponse<DocumentGet>> updateDocument(
            @Valid @ModelAttribute("uploadForm") DocumentUpdate object) {
        return documentMethods.update(object, 0L);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).DOCUMENT_MANAGE)")
    @DeleteMapping("/document/{id}")
    public ResponseEntity<APIResponse<DocumentGet>> deleteDocument(
            @PathVariable(name = "id") Long id){
        return documentMethods.delete(id, false, 0L);
    }
}
