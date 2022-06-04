package com.databasir.api.config.security;

import com.databasir.dao.tables.pojos.User;
import com.databasir.dao.tables.pojos.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DatabasirUserDetails implements UserDetails {

    @Getter
    private final User user;

    @Getter
    private final List<UserRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> {
                    String expression = role.getRole();
                    if (role.getGroupId() != null) {
                        expression += "?groupId=" + role.getGroupId();
                    }
                    return new SimpleGrantedAuthority(expression);
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
