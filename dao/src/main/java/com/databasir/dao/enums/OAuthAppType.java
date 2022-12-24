package com.databasir.dao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OAuthAppType {

    GITHUB("github"), GITLAB("gitlab"), WE_WORK("企业微信");

    @Getter
    private String description;

    public boolean isSame(String type) {
        return this.name().equalsIgnoreCase(type);
    }
}
