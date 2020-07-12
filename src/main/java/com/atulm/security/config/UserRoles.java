package com.atulm.security.config;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserRoles {

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(UserPermission.COURSE_READ, UserPermission.COURSE_WRIGHT,
            UserPermission.STUDENT_READ, UserPermission.STUDENT_WRIGHT)),
    ADMIN_TRAINEE(Sets.newHashSet(UserPermission.COURSE_READ, UserPermission.STUDENT_READ));

    @Getter
    private final Set<UserPermission> permissions;


    UserRoles(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getPermissions().stream().map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet()) ;
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return simpleGrantedAuthorities;
    }
}
