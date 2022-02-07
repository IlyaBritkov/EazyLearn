package com.eazylearn.enums;

public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String toString() {
        return this.name().substring("ROLE_".length());
    }
}
