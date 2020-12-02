package vn.com.mta.science.module.user.service;

import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.repository.service.detail.VoidableGeneratedIDSchemaService;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.module.user.schema.*;

public interface UserService extends VoidableGeneratedIDSchemaService<UserGet, User> {

    Long getUserId(String username) throws APIException;

    UserGet getByUsername(String username) throws APIException;

    UserGet1 login(Credential credential) throws APIException;

    PasswordChangeGet changePassword(Long userId, PasswordChangeCreate object, Long callerId) throws APIException;
}
