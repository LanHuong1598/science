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
import vn.com.mta.science.module.model.Invention;
import vn.com.mta.science.module.schema.DocumentCreate;
import vn.com.mta.science.module.schema.DocumentGet;
import vn.com.mta.science.module.schema.InventionCreate;
import vn.com.mta.science.module.schema.InventionGet;
import vn.com.mta.science.module.service.DocumentService;
import vn.com.mta.science.module.service.InventionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/secured/ws/rest/v1/")
public class InventionAPI {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventionService inventionService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<InventionGet, Invention> inventionMethod;

    @Bean
    @DependsOn({"inventionService"})
    public GeneratedIDSchemaAPIMethod<InventionGet, Invention> getInventionMethod() {
        return new GeneratedIDSchemaAPIMethod<>(logger, inventionService);
    }

    @GetMapping("/invention")
    public ResponseEntity<APIResponse<List<InventionGet>>> getAllInvention() {
        return inventionMethod.getList(null, 0, 0, true);
    }

    @GetMapping("/invention/{id}")
    public ResponseEntity<APIResponse<InventionGet>> getInvention(@PathVariable(name = "id") Long id) {
        return inventionMethod.getById(id);
    }

    @PostMapping("/invention")
    public ResponseEntity<APIResponse<InventionGet>> create(
            @Valid @RequestBody InventionCreate object) {
        return inventionMethod.create(object, 0L);
    }
}
