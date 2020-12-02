package vn.com.mta.science.module.user.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.com.mta.science.module.user.model.User;
import vn.com.mta.science.module.user.model.Role;
import vn.com.mta.science.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter @Setter @NoArgsConstructor
public class ItechUserDetails implements UserDetails {

    private Long id;

    private String uuid;

    private String username;

    private String password;

    private boolean enabled;

    private Collection<GrantedAuthority> authorities;

    public ItechUserDetails(User user) {
        id = user.getId();
        uuid = user.getUuid();
        password = user.getPassword();
        username = user.getUsername();
        enabled = user.getEnabled();
        authorities = new HashSet<>();

        if(user.getRoles() != null) {
            Set<String> authSet = new TreeSet<>();
            for(Role role : user.getRoles()) authSet.addAll(CollectionUtils.toSet(role.getAuthorities()));
            for (String auth : authSet) authorities.add((GrantedAuthority) () -> auth);
        }
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return enabled; }
}
