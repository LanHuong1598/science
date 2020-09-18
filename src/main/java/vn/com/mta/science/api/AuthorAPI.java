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
import vn.com.mta.science.module.model.Author;
import vn.com.mta.science.module.schema.AuthorCreate;
import vn.com.mta.science.module.schema.AuthorGet;
import vn.com.mta.science.module.service.AuthorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/secured/ws/rest/v1/")
public class AuthorAPI {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<AuthorGet, Author> authorMethod;

    @Bean
    @DependsOn({"authorService"})
    public GeneratedIDSchemaAPIMethod<AuthorGet, Author> getAuthorMethod() {
        return new GeneratedIDSchemaAPIMethod<>(logger, authorService);
    }

    @GetMapping("/author")
    public ResponseEntity<APIResponse<List<AuthorGet>>> getAllDocument() {
        return authorMethod.getList(null, 0, 0, true);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<APIResponse<AuthorGet>> getDocumentById(@PathVariable(name = "id") Long id) {
        return authorMethod.getById(id);
    }

    @PostMapping("/author")
    public ResponseEntity<APIResponse<AuthorGet>> createDocument(
            @Valid @RequestBody AuthorCreate object) {
        return authorMethod.create(object, 0L);
    }
}
