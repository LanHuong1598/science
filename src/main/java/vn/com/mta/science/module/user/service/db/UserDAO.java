package vn.com.mta.science.module.user.service.db;

import vn.com.itechcorp.base.repository.dao.VoidableDAO;
import vn.com.mta.science.module.user.model.User;

public interface UserDAO extends VoidableDAO<User, Long> {

    User getByUserName(String username);

}
