package vn.com.mta.science.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.itechcorp.base.api.response.APIListResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.api.response.APIResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
import vn.com.itechcorp.base.exception.APIAuthenticationException;
import vn.com.itechcorp.base.exception.ObjectNotFoundException;
import vn.com.mta.science.module.model.Invention;
import vn.com.mta.science.module.model.Menus;
import vn.com.mta.science.module.schema.DocumentGet;
import vn.com.mta.science.module.schema.InventionGet;
import vn.com.mta.science.module.schema.Stats;
import vn.com.mta.science.module.schema.ocr;
import vn.com.mta.science.module.service.DocumentService;
import vn.com.mta.science.module.service.InventionService;
import vn.com.mta.science.module.service.StaffBookmarkService;
import vn.com.mta.science.module.service.filter.StatsFilter;
import vn.com.mta.science.module.user.auth.ItechUserUtil;
import vn.com.mta.science.module.user.schema.Credential;
import vn.com.mta.science.module.user.schema.UserGet1;
import vn.com.mta.science.module.user.service.UserService;

import javax.validation.Valid;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/secured/ws/rest/v1/")
public class testAPI {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StaffBookmarkService staffBookmarkService;

    @Autowired
    InventionService inventionService;

    @PreAuthorize("permitAll()")
    @PostMapping("/test")
    public ResponseEntity<ocr> test(@Valid @RequestBody MultipartFile multipartFile) {
        try {
            return ResponseEntity.ok().body(staffBookmarkService.getImage(multipartFile));
        } catch (ObjectNotFoundException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (APIAuthenticationException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/stats")
    public ResponseEntity<APIResponse<List<Menus>>> test() {
        try {
            List<Menus> results = staffBookmarkService.getDocumentTypeMenus();
            APIResponse<List<Menus>> response = results == null ?
                    new APIResponse<>(new APIResponseHeader(APIResponseStatus.NOT_FOUND, "No record found"), null)
                    : new APIResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, results.size() + " record(s) found", 0, 0, results.size()), results);

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

    @PreAuthorize("permitAll()")
    @PostMapping("/statsbyyear")
    public ResponseEntity<APIResponse<Stats>> stats(
            @RequestBody StatsFilter statsFilter,
            Authentication basicAuth
            ) {
        try {
            Stats results = staffBookmarkService.getStats(statsFilter, ItechUserUtil.extractUserId(basicAuth));
            APIResponse<Stats> response = results == null ?
                    new APIResponse<>(new APIResponseHeader(APIResponseStatus.NOT_FOUND, "No record found"), null)
                    : new APIResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, results.getDs().size() + " record(s) found", 0, 0, results.getDs().size()), results);

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

    @PreAuthorize("permitAll()")
    @PostMapping("/statsbyyear/getfile")
    public ResponseEntity<APIResponse<String>> statsGetFile(  Authentication basicAuth,
            @RequestBody StatsFilter statsFilter
    ) {
        try {
            String results = staffBookmarkService.getStatsFile(statsFilter, ItechUserUtil.extractUserId(basicAuth));
            APIResponse<String> response = results == null ?
                    new APIResponse<>(new APIResponseHeader(APIResponseStatus.NOT_FOUND, "No record found"), null)
                    : new APIResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND,  " record(s) found", 0, 0, 1), results);

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

    @PreAuthorize("permitAll()")
    @GetMapping("/download/{type}/{id}/{token}")
    public ResponseEntity<byte[]> getFilePDF(@PathVariable(name = "id") String id,
                                             @PathVariable(name = "type") String type,
                                             @PathVariable(name = "token") String token) {
        try {

            //token = token.substring(6);
            token = new String(Base64.getDecoder().decode(token));
            String[] arr = token.split(":");
            Credential credential = new Credential();
            credential.setUsername(arr[0]);
            credential.setPassword(arr[1]);

            if (type.equals("document")) {
                DocumentGet document = documentService.getByUuid(id);

                UserGet1 user = userService.login(credential);
                if (!user.getRoles().stream().map(m -> m.getName()).collect(Collectors.toList()).contains("sysadmin")
                        && !document.getAuthors().contains(user.getId())) {
                    throw new APIAuthenticationException();
                }
            }
            else {
                InventionGet document = inventionService.getByUuid(id);

                UserGet1 user = userService.login(credential);
                if (!user.getRoles().stream().map(m -> m.getName()).collect(Collectors.toList()).contains("sysadmin")
                        && !document.getAuthors().contains(user.getId())) {
                    throw new APIAuthenticationException();
                }
            }


            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("X-Frame-Options", "SAMEORIGIN");
            responseHeaders.set("Content-Type", "application/pdf");

            return ResponseEntity.ok()
                    .headers(responseHeaders)
//                    .contentType(MediaType.APPLICATION_PDF)
                    .body(staffBookmarkService.getPDFB(type, id));

        } catch (ObjectNotFoundException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (APIAuthenticationException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    DocumentService documentService;

    @PreAuthorize("permitAll()")
    @GetMapping("/updatepublishdate")
    public ResponseEntity<String> updatepublishdate() {
        try {
            documentService.updatedb();
            return ResponseEntity.ok().body("ok");
        } catch (ObjectNotFoundException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (APIAuthenticationException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/mail")
    public ResponseEntity<String> mail() {
        try {
            staffBookmarkService.createSheet();
            return ResponseEntity.ok().body("ok");
        } catch (ObjectNotFoundException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (APIAuthenticationException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/api/download/{type}/{id}/byte")
    public ResponseEntity<byte[]> getFilePDFByte(@PathVariable(name = "id") String id,
                                             @PathVariable(name = "type") String type) {
        try {
            return ResponseEntity.ok().body(staffBookmarkService.getPDFB(type, id));
        } catch (ObjectNotFoundException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (APIAuthenticationException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Autowired
    UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<APIResponse<UserGet1>> login( @RequestBody Credential credential) {
        try {
            return new ResponseEntity<>(
                    new APIResponse<>(new APIListResponseHeader(
                            APIResponseStatus.OK, "Logged in", 0, 0, 1),
                            userService.login(credential)), HttpStatus.OK);
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
