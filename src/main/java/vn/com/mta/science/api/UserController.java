package vn.com.mta.science.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.method.GeneratedIDSchemaAPIMethod;
import vn.com.itechcorp.base.api.method.SchemaAPIMethod;
import vn.com.itechcorp.base.api.response.APIListResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.api.response.APIResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
import vn.com.itechcorp.base.exception.*;
import vn.com.mta.science.module.user.auth.ItechUserUtil;
import vn.com.mta.science.module.user.filter.UserFilter;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.module.user.schema.*;
import vn.com.mta.science.module.user.service.RoleService;
import vn.com.mta.science.module.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
@Api(value = "user_api", tags = "user-api")
@RequestMapping("/secured/ws/rest/v1")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Bean
    @DependsOn({"userService"})
    public GeneratedIDSchemaAPIMethod<UserGet, User> getExtUserAPI() {
        return new GeneratedIDSchemaAPIMethod<>(logger, userService);
    }

    @Autowired
    private GeneratedIDSchemaAPIMethod<UserGet, User> extUserAPI;

    @PreAuthorize("permitAll()")
    @GetMapping("/me")
    public ResponseEntity<APIResponse<UserGet>> getInfo(Authentication basicAuth) {
        try {

            UserGet results = userService.getById(ItechUserUtil.extractUserId(basicAuth));
            APIResponse<UserGet> response = results == null ?
                    new APIResponse<>(new APIResponseHeader(APIResponseStatus.NOT_FOUND, "No record found"), null)
                    : new APIResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, " record(s) found", 0, 0, 1), results);

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

    @ApiOperation(value = "Creates password for a user with the given ID")
    @PreAuthorize("permitAll()")
    @PostMapping("/me/password")
    public ResponseEntity<APIResponse<PasswordChangeGet>> changePassword(
            Authentication basicAuth,
            @Valid @RequestBody PasswordChangeCreate object) {

        try {
            Long userId = ItechUserUtil.extractUserId(basicAuth);
            PasswordChangeGet results = userService.changePassword(userId, object, userId);
            APIResponse<PasswordChangeGet> response = results == null ?
                    new APIResponse<>(new APIResponseHeader(APIResponseStatus.NOT_FOUND, "No record found"), null)
                    : new APIResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, " record(s) found", 0, 0, 1), results);

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


    @ApiOperation(value = "View a list of available users")
    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).USER_GET)")
    @GetMapping("/user")
    public ResponseEntity<APIResponse<List<UserGet>>> getAllUsers(
            @RequestParam(required = false, name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
            @RequestParam(required = false, name = "orderAsc", defaultValue = "true") boolean orderAsc) {
        return extUserAPI.getList(orderBy, offset, limit, orderAsc);
    }

    @ApiOperation(value = "Search users with filtering conditions")
    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).USER_GET)")
    @PostMapping("/search/user")
    public ResponseEntity<APIResponse<List<UserGet>>> searchUsers(
            @RequestParam(required = false, name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
            @RequestParam(required = false, name = "orderAsc", defaultValue = "true") boolean orderAsc,
            @Valid @RequestBody UserFilter filter) {
        return extUserAPI.search(orderBy, offset, limit, orderAsc, filter);
    }

    @ApiOperation(value = "Search a user with an ID")

    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).USER_GET)")
    @GetMapping("/user/{id}")
    public ResponseEntity<APIResponse<UserGet>> getUserById(@PathVariable(name = "id") Long id) {
        return extUserAPI.getById(id);
    }

    @ApiOperation(value = "Inserts the given user into the database")
    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).USER_MANAGE)")
    @PostMapping("/user")
    public ResponseEntity<APIResponse<UserGet>> createUser(Authentication basicAuth,
                                                           @Valid @RequestBody UserCreate object) {
        return extUserAPI.create(object, ItechUserUtil.extractUserId(basicAuth));
    }

    @ApiOperation(value = "Updates the given user in the database")
    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).USER_MANAGE)")
    @PutMapping("/user")
    public ResponseEntity<APIResponse<UserGet>> updateUser(
            Authentication basicAuth,
            @Valid @RequestBody UserUpdate object) {
        return extUserAPI.update(object, ItechUserUtil.extractUserId(basicAuth));
    }

    @ApiOperation(value = "Deletes a user with the given ID")
    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).USER_MANAGE)")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<APIResponse<UserGet>> deleteUser(Authentication basicAuth,
                                                           @PathVariable(name = "id") Long id,
                                                           @RequestParam(required = false, name = "purge", defaultValue = "false") boolean purge) {
        return extUserAPI.delete(id, purge, ItechUserUtil.extractUserId(basicAuth));
    }

    // password
    @ApiOperation(value = "Creates password for a user with the given ID")
    @PreAuthorize("hasAnyAuthority(T(vn.com.mta.science.util.ItechAuthority).SYSADMIN, "
            + "T(vn.com.mta.science.util.ItechAuthority).USER_MANAGE)")
    @PostMapping("/user/{id}/password")
    public ResponseEntity<APIResponse<PasswordChangeGet>> changePassword(Authentication basicAuth,
                                                                         @PathVariable(name = "id") Long id,
                                                                         @Valid @RequestBody PasswordChangeCreate object) {

        try {
            PasswordChangeGet results = userService.changePassword(id, object, ItechUserUtil.extractUserId(basicAuth));

            APIResponse<PasswordChangeGet> response = results == null ?
                    new APIResponse<>(new APIResponseHeader(APIResponseStatus.NOT_FOUND, "No record found"), null)
                    : new APIResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, " record(s) found", 0, 0, 1), results);

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

    @Autowired
    private RoleService roleService;

    @Bean
    @DependsOn({"roleService"})
    public SchemaAPIMethod<RoleGet, Role, String> getExtRoleAPI() {
        return new SchemaAPIMethod<>(logger, roleService);
    }

    @Autowired
    private SchemaAPIMethod<RoleGet, Role, String> extRoleAPI;

    @ApiOperation(value = "View a list of available roles")
    @PreAuthorize("hasAnyAuthority(T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).SYSADMIN, " +
            "T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).ROLE_GET)")
    @GetMapping("/role")
    public ResponseEntity<APIResponse<List<RoleGet>>> getAllRoles(
            @RequestParam(required = false, name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
            @RequestParam(required = false, name = "orderAsc", defaultValue = "true") boolean orderAsc) {
        return extRoleAPI.getList(orderBy, offset, limit, orderAsc);
    }

    @ApiOperation(value = "Search a role with an ID")
    @PreAuthorize("hasAnyAuthority(T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).SYSADMIN, " +
            "T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).ROLE_GET)")
    @GetMapping("/role/{id}")
    public ResponseEntity<APIResponse<RoleGet>> getRoleById(@PathVariable(name = "id") String id) {
        return extRoleAPI.getById(id);
    }

    @ApiOperation(value = "Inserts the given role into the database")
    @PreAuthorize("hasAnyAuthority(T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).SYSADMIN, " +
            "T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).ROLE_MANAGE)")
    @PostMapping("/role")
    public ResponseEntity<APIResponse<RoleGet>> createRole(Authentication basicAuth,
                                                           @Valid @RequestBody RoleCreate object) {
        return extRoleAPI.create(object, ItechUserUtil.extractUserId(basicAuth));
    }

    @ApiOperation(value = "Updates the given role in the database")
    @PreAuthorize("hasAnyAuthority(T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).SYSADMIN," +
            "T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).ROLE_MANAGE)")
    @PutMapping("/role")
    public ResponseEntity<APIResponse<RoleGet>> updateRole(Authentication basicAuth,
                                                           @Valid @RequestBody RoleUpdate object) {
        return extRoleAPI.update(object, ItechUserUtil.extractUserId(basicAuth));
    }

    @ApiOperation(value = "Deletes a role with the given ID")
    @PreAuthorize("hasAnyAuthority(T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).SYSADMIN," +
            " T(vn.com.itechcorp.pacs.core.module.user.auth.ItechAuthority).ROLE_MANAGE)")
    @DeleteMapping("/role/{id}")
    public ResponseEntity<APIResponse<RoleGet>> deleteRole(Authentication basicAuth,
                                                           @PathVariable(name = "id") String id,
                                                           @RequestParam(required = false, name = "purge", defaultValue = "false") boolean purge) {
        return extRoleAPI.delete(id, purge, ItechUserUtil.extractUserId(basicAuth));
    }
}
