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
import vn.com.mta.science.module.model.Invention;
import vn.com.mta.science.module.schema.*;
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

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).INVENTION_GET)")
    @GetMapping("/invention")
    public ResponseEntity<APIResponse<List<InventionGet>>> getAllInvention() {
        return inventionMethod.getList(null, 0, 0, true);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).INVENTION_GET)")
    @GetMapping("/invention/{id}")
    public ResponseEntity<APIResponse<InventionGet>> getInvention(@PathVariable(name = "id") Long id) {
        return inventionMethod.getById(id);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).INVENTION_MANAGE)")
    @PostMapping("/invention")
    public ResponseEntity<APIResponse<InventionGet>> create(
            @Valid @ModelAttribute("uploadForm") InventionCreate object) {
        return inventionMethod.create(object, 0L);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).INVENTION_MANAGE)")
    @PutMapping("/invention")
    public ResponseEntity<APIResponse<InventionGet>> edit(
            @Valid @ModelAttribute("uploadForm") InventionUpdate object) {
        return inventionMethod.update(object, 0L);
    }

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).INVENTION_MANAGE)")
    @DeleteMapping("/invention/{id}")
    public ResponseEntity<APIResponse<InventionGet>> delete(@PathVariable(name = "id") Long id) {
        return inventionMethod.delete(id, false, 0l);
    }
}
