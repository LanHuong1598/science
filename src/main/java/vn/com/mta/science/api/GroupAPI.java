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
import vn.com.mta.science.module.model.ResearchGroup;
import vn.com.mta.science.module.schema.GroupCreate;
import vn.com.mta.science.module.schema.GroupGet;
import vn.com.mta.science.module.service.GroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/secured/ws/rest/v1/")
public class GroupAPI {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupService groupService;

    @Autowired
    private GeneratedIDSchemaAPIMethod<GroupGet, ResearchGroup> groupMethods;

    @Bean
    @DependsOn({"groupService"})
    public GeneratedIDSchemaAPIMethod<GroupGet, ResearchGroup> get() {
        return new GeneratedIDSchemaAPIMethod<>(logger, groupService);
    }

    @GetMapping("/group")
    public ResponseEntity<APIResponse<List<GroupGet>>> getAllDocument() {
        return groupMethods.getList(null, 0, 0, true);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<APIResponse<GroupGet>> getDocumentById(@PathVariable(name = "id") Long id) {
        return groupMethods.getById(id);
    }

    @PostMapping("/group")
    public ResponseEntity<APIResponse<GroupGet>> createDocument(
            @Valid @RequestBody GroupCreate object) {
        return groupMethods.create(object, 0L);
    }
}
