package com.databasir.core.domain.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditLog {

    String module();

    String name() default "UNKNOWN";

    /**
     * @return the Spring-EL expression
     */
    String involvedProjectId() default "N/A";

    /**
     * @return the Spring-EL expression
     */
    String involvedGroupId() default "N/A";

    /**
     * @return the Spring-EL expression
     */
    String involvedUserId() default "N/A";

    boolean retrieveInvolvedGroupId() default false;

    interface Modules {
        String UNKNOWN = "UNKNOWN";
        String PROJECT = "project";
        String USER = "user";
        String GROUP = "group";
        String LOGIN_APP = "login_app";
        String SETTING = "setting";
        String DATABASE_TYPE = "database_type";
        String LOGIN = "login";
    }

    interface Types {
        int SYSTEM_USER_ID = -1;
    }
}
