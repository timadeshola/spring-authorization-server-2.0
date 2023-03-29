package com.example.oauthserver.core.security;

import com.example.oauthserver.model.enums.AppStatus;
import com.example.oauthserver.persistence.entity.Privilege;
import com.example.oauthserver.persistence.entity.Role;
import com.example.oauthserver.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Project title: oauth-server
 *
 * @author johnadeshola
 * Date: 3/28/23
 * Time: 11:16 AM
 */
@Service
@RequiredArgsConstructor
public class AuthorityDetails {

    private static Set<String> getRoles(User user) {
        Set<String> roles = new HashSet<>();
        Set<Role> collection = new HashSet<>(user.getRoles());
        for (Role role : collection) {
            if (role.getStatus().getStatus().equals(AppStatus.ACTIVE.getStatus())) {
                roles.add(role.getAuthority().toUpperCase(Locale.ENGLISH));
            } else {
                roles.remove(role.getAuthority());
            }
        }
        return roles;
    }

    private static Set<String> getPrivileges(User user) {
        Set<String> privileges = new HashSet<>();
        Set<Privilege> collection = new HashSet<>();
        for (Role role : user.getRoles()) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege privilege : collection) {
            if (privilege.getStatus().getStatus().equals(AppStatus.ACTIVE.getStatus())) {
                privileges.add(privilege.getAuthority().toUpperCase(Locale.ENGLISH));
            } else {
                privileges.remove(privilege.getAuthority());
            }
        }
        return privileges;
    }

    private static Set<GrantedAuthority> grantedAuthorities(Set<String> privileges, Set<String> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority("PERMISSION_" + privilege.toUpperCase(Locale.ENGLISH)));
        }

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase(Locale.ENGLISH)));
        }
        return authorities;
    }

    public Set<? extends GrantedAuthority> getAuthorities(User user) {
        return grantedAuthorities(getPrivileges(user), getRoles(user));
    }

}
