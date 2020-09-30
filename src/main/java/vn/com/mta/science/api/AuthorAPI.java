package vn.com.mta.science.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.method.GeneratedIDSchemaAPIMethod;
import vn.com.itechcorp.base.api.response.APIListResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.api.response.APIResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
import vn.com.itechcorp.base.exception.APIAuthenticationException;
import vn.com.itechcorp.base.repository.dao.PaginationInfo;
import vn.com.itechcorp.base.util.Pair;
import vn.com.mta.science.module.model.Author;
import vn.com.mta.science.module.schema.AuthorCreate;
import vn.com.mta.science.module.schema.AuthorGet;
import vn.com.mta.science.module.schema.AuthorUpdate;
import vn.com.mta.science.module.service.AuthorService;
import vn.com.mta.science.module.service.filter.AuthorByAffFilter;

import javax.validation.Valid;
import java.util.Collection;
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
        return authorMethod.getList("id", 0, 10000 , false);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<APIResponse<AuthorGet>> getDocumentById(@PathVariable(name = "id") Long id) {
        return authorMethod.getById(id);
    }

    @PostMapping("/author")
    public ResponseEntity<APIResponse<AuthorGet>> createAuthor(
            @Valid @RequestBody AuthorCreate object) {
        return authorMethod.create(object, 0L);
    }

    @PutMapping("/author")
    public ResponseEntity<APIResponse<AuthorGet>> updateAuthor(
            @Valid @RequestBody AuthorUpdate object) {
        return authorMethod.update(object, 0L);
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<APIResponse<AuthorGet>> deleteAuthor(
            @PathVariable(name = "id") Long id){
        return authorMethod.delete(id, false, 0L);
    }

    @PostMapping("/search/authorByAff")
    public ResponseEntity<APIResponse<Collection<AuthorGet>>> searchAuthorByAff(
            @Valid @RequestBody AuthorByAffFilter filter) {
        try {
            Pair<Collection<AuthorGet>, Long> results = authorService.getByAff(filter);
            APIResponse<Collection<AuthorGet>> response = results.getFirst() == null ?
                    new APIResponse<>(new APIResponseHeader(APIResponseStatus.NOT_FOUND, "No record found"), null)
                    : new APIResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, results.getFirst().size() + " record(s) found", 0, 0, results.getSecond()), results.getFirst());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (APIAuthenticationException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(new APIResponse<>(new APIResponseHeader(APIResponseStatus.UNAUTHORIZED, ex.getMessage()), null),
                    HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(new APIResponse<>(new APIResponseHeader(APIResponseStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
