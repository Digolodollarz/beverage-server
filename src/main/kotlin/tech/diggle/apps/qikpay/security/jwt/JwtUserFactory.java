package tech.diggle.apps.qikpay.security.jwt;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tech.diggle.apps.qikpay.security.authority.Authority;
import tech.diggle.apps.qikpay.security.authority.AuthorityName;
import tech.diggle.apps.qikpay.security.user.AppUser;

//@Service
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(AppUser appUser) {
        return new JwtUser(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getFirstname(),
                appUser.getLastname(),
                appUser.getEmail(),
                appUser.getPassword(),
                mapToGrantedAuthorities(appUser.getAuthorities()),
                appUser.getEnabled(),
                appUser.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }

    private static List<Authority> mapToAuthorities(Collection<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> {
                    Authority auth = new Authority();
                    auth.setName(AuthorityName.valueOf(authority.getAuthority()));
                    return auth;
                })
                .collect(Collectors.toList());
    }

    public static AppUser user(JwtUser user) {
        AppUser usr = new AppUser();
        usr.setUsername(user.getUsername());
        usr.setFirstname(user.getFirstname());
        usr.setLastname(user.getLastname());
        usr.setEmail(user.getEmail());
        usr.setPassword(user.getPassword());
        usr.setAuthorities(mapToAuthorities(user.getAuthorities()));
        usr.setEnabled(user.isEnabled());
        usr.setLastPasswordResetDate(usr.getLastPasswordResetDate());
        return usr;
    }
}

