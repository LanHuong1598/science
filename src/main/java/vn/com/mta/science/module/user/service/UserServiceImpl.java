package vn.com.mta.science.module.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIAuthenticationException;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.exception.IllegalPropertyException;
import vn.com.itechcorp.base.exception.ObjectNotFoundException;
import vn.com.itechcorp.base.repository.dao.PaginationInfo;
import vn.com.itechcorp.base.repository.filter.BaseFilter;
import vn.com.mta.science.module.user.auth.ItechUserPasswordEncoder;
import vn.com.mta.science.module.user.filter.UserFilter;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.module.user.schema.*;
import vn.com.itechcorp.base.repository.service.detail.impl.VoidableGeneratedIDSchemaServiceImpl;
import vn.com.itechcorp.base.util.Pair;
import vn.com.mta.science.module.user.service.db.UserDAO;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl extends VoidableGeneratedIDSchemaServiceImpl<UserGet, User> implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDAO getDAO() {
        return userDAO;
    }

    @Override
    public UserGet convert(User object) {
        UserGet response = new UserGet(object);

        return response;
    }

    @Override
    public Long getUserId(String username) throws APIException {
        if (username == null) return null;

        User user = getDAO().getByUserName(username);
        if (user == null) return null;
        return user.getId();
    }

    @Override
    public UserGet getByUsername(String username) throws APIException {
        User user = getDAO().getByUserName(username);
        if (user == null) throw new ObjectNotFoundException("Invalid {username}: " + username);
        return convert(user);
    }

    @Autowired
    RoleService roleService;

    @Override
    public UserGet1 login(Credential credential) throws APIException {

        User user = getDAO().getByUserName(credential.getUsername().toLowerCase());
        if (user == null) throw new APIAuthenticationException("Invalid credential");

        if (!ItechUserPasswordEncoder.getInstance().matches(credential.getPassword(), user.getPassword()))
            throw new APIAuthenticationException("Invalid credential");

        UserGet1 userGet1 = new UserGet1();
        userGet1.setToken("Basic " + Base64.getEncoder().encodeToString((credential.getUsername().toLowerCase() + ":" + credential.getPassword()).getBytes()));
        userGet1.setAu("user");
        for (Role role : user.getRoles()) {
            if (role.getId().equals("sysadmin")) {
                userGet1.setAu("admin");
            }
        }
        userGet1.setRoles(user.getRoles().stream().map(x -> new RoleGet(x)).collect(Collectors.toSet()));
        return userGet1;
    }

    @Override
    public PasswordChangeGet changePassword(Long userId, PasswordChangeCreate object, Long callerId) throws APIException {
        User user = getDAO().getById(userId);

        if (user == null) throw new IllegalPropertyException("Invalid property {userId}");

        if (!ItechUserPasswordEncoder.getInstance().matches(object.getCurrentPassword(), user.getPassword()))
            throw new IllegalPropertyException("Invalid properties {currentPassword}");

        user.setPassword(ItechUserPasswordEncoder.getInstance().encode(object.getNewPassword()));
        getDAO().update(user, callerId);

        // change to new password
        PasswordChangeGet password = new PasswordChangeGet();
        password.setLength(object.getNewPassword().length());
        password.setHint(object.getHint());
        password.setToken("Basic " + Base64.getEncoder().encodeToString((user.getUsername() + ":" + object.getNewPassword()).getBytes()));

        return password;
    }

    @Override
    public List<UserGet> getPageOfData(PaginationInfo pagingInfo) throws APIException {
        UserFilter userFilter = new UserFilter();
        userFilter.setHidden(false);
        return super.getPageOfData(userFilter, pagingInfo);
    }

    @Override
    public List<UserGet> getPageOfData(boolean includeVoided, PaginationInfo pagingInfo) throws APIException {
        UserFilter userFilter = new UserFilter();
        userFilter.setHidden(false);
        return super.getPageOfData(userFilter, includeVoided, pagingInfo);
    }

    @Override
    public Pair<List<UserGet>, Long> getPageOfDataWithTotal(boolean includeVoided, PaginationInfo pagingInfo) throws APIException {
        UserFilter userFilter = new UserFilter();
        userFilter.setHidden(false);
        return super.getPageOfDataWithTotal(userFilter, includeVoided, pagingInfo);
    }

    @Override
    public List<UserGet> getPageOfData(BaseFilter filter, boolean includeVoided, PaginationInfo pagingInfo) {
        if (filter instanceof UserFilter) {
            UserFilter userFilter = (UserFilter) filter;
            userFilter.setHidden(false);
            return super.getPageOfData(userFilter, includeVoided, pagingInfo);
        }
        return super.getPageOfData(filter, includeVoided, pagingInfo);
    }

    @Override
    public Pair<List<UserGet>, Long> getPageOfDataWithTotal(BaseFilter filter, boolean includeVoided, PaginationInfo pagingInfo) throws APIException {
        if (filter instanceof UserFilter) {
            UserFilter userFilter = (UserFilter) filter;
            userFilter.setHidden(false);
            return super.getPageOfDataWithTotal(userFilter, includeVoided, pagingInfo);
        }
        return super.getPageOfDataWithTotal(filter, includeVoided, pagingInfo);
    }

    @Override
    public Pair<List<UserGet>, Long> getPageOfDataWithTotal(BaseFilter filter, PaginationInfo pagingInfo) throws APIException {
        if (filter instanceof UserFilter) {
            UserFilter userFilter = (UserFilter) filter;
            userFilter.setHidden(false);
            return super.getPageOfDataWithTotal(userFilter, pagingInfo);
        }
        return super.getPageOfDataWithTotal(filter, pagingInfo);
    }

}
