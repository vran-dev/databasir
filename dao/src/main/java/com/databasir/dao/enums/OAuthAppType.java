package com.databasir.dao.enums;

public enum OAuthAppType {

    GITHUB, GITLAB;

    public boolean isSame(String type) {
        return this.name().equalsIgnoreCase(type);
    }
}
