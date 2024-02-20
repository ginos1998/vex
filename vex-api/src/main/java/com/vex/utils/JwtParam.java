package com.vex.utils;

public enum JwtParam {
    USERNAME("username"),
    ROLES("roles");

    private final String value;

    JwtParam(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
