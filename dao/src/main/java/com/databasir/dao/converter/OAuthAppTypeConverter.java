package com.databasir.dao.converter;

import com.databasir.dao.enums.OAuthAppType;
import org.jooq.impl.EnumConverter;

public class OAuthAppTypeConverter extends EnumConverter<String, OAuthAppType> {

    public OAuthAppTypeConverter() {
        super(String.class, OAuthAppType.class);
    }

}
