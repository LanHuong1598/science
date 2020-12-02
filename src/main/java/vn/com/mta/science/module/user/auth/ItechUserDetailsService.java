package vn.com.mta.science.module.user.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.module.user.service.db.UserDAO;

@Transactional
@Service("itechUserDetailsService")
public class ItechUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public ItechUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getByUserName(username);
        if(user == null) throw new UsernameNotFoundException("user " + username + " not found");
        if(!user.getEnabled()) throw new UsernameNotFoundException("user " + username + " is disabled");
        if(user.isVoided()) throw new UsernameNotFoundException("user " + username + " is voided");

        return new ItechUserDetails(user);
    }
}
