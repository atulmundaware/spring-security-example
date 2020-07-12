package com.atulm.security.config;

import lombok.Getter;

public enum UserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRIGHT("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRIGHT("course:write");

    @Getter
    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

}
