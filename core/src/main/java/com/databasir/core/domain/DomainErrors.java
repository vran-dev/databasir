package com.databasir.core.domain;

import com.databasir.common.DatabasirErrors;
import com.databasir.common.DatabasirException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DomainErrors implements DatabasirErrors {
    REFRESH_TOKEN_EXPIRED("X_0001"),
    INVALID_REFRESH_TOKEN_OPERATION("X_0002"),
    NETWORK_ERROR("error.network.timeout"),
    INVALID_ACCESS_TOKEN("X_0004"),
    MISS_REQUIRED_PARAMETERS("error.parameter.required"),

    DATABASE_META_NOT_FOUND("error.database.metadata.get-failed"),
    DATABASE_CONNECT_FAILED("error.database.connect-failed"),
    DATABASE_TYPE_NOT_SUPPORT("error.database.type.not-supported"),
    DATABASE_TYPE_NAME_DUPLICATE("error.database.type.name-duplicate"),
    DATABASE_MUST_NOT_MODIFY_SYSTEM_DEFAULT_TYPE("error.database.type.must-not-modify-default-type"),

    DRIVER_UPLOAD_FAILED("error.database.driver.upload-failed"),
    DRIVER_URL_AND_PATH_MUST_NOT_BE_ALL_BLANK("error.database.driver.url-or-path-invalid"),
    DRIVER_LOAD_FAILED("error.database.driver.load-failed"),
    DRIVER_CLASS_NOT_FOUND("error.database.driver.class-not-found"),
    DRIVER_DOWNLOAD_FAILED("error.database.driver.download-failed"),
    INVALID_DATABASE_TYPE_URL_PATTERN("error.database.url-pattern.invalid"),
    MISS_DB_URL("error.database.url-patter.miss-db-url"),
    MISS_JDBC_PROTOCOL("error.database.url-patter.miss-protocol"),
    MISS_DB_SCHEMA("error.database.url-patter.miss-db-schema"),

    PASSWORD_MUST_NOT_BE_BLANK("error.user.password.must-not-be-blank"),
    USERNAME_OR_EMAIL_DUPLICATE("error.user.username-or-email-duplicate"),
    USER_ROLE_DUPLICATE("error.user.role.duplicate"),
    UPDATE_PASSWORD_CONFIRM_FAILED("error.user.password.not-match"),
    CANNOT_DELETE_SELF("error.user.must-not-delete-self"),
    ORIGIN_PASSWORD_NOT_CORRECT("error.user.password.invalid"),
    CANNOT_UPDATE_SELF_ROLE("error.user.role.must-not-update-self"),
    CANNOT_UPDATE_SELF_ENABLED_STATUS("error.user.role.must-not-update-self"),

    PROJECT_NOT_FOUND("error.project.not-found"),
    PROJECT_NAME_DUPLICATE("error.project.name-duplicate"),
    INVALID_CRON_EXPRESSION("error.project.cron-invalid"),

    REGISTRATION_ID_DUPLICATE("error.login.app.registration-id-duplicate"),
    REGISTRATION_ID_NOT_FOUND("error.login.app.registration-id-not-found"),
    MISS_REDIRECT_URI("error.login.app.miss-redirect-uri"),

    DOCUMENT_VERSION_IS_INVALID("error.document.version-invalid"),
    DATABASE_DOCUMENT_DUPLICATE_KEY("error.document.version-duplicate"),
    TABLE_META_NOT_FOUND("error.document.table.not-found"),

    INVALID_MOCK_DATA_SCRIPT("error.script.mock.expression-invalid"),
    MOCK_DATA_SCRIPT_MUST_NOT_BE_BLANK("error.script.mock.is-blank"),
    DEPENDENT_COLUMN_NAME_MUST_NOT_BE_BLANK("error.script.mock.dependent-column-name-required"),
    MUST_NOT_REF_SELF("error.script.mock.dependent-must-not-ref-self"),
    CIRCLE_REFERENCE("error.script.mock.dependent-circle-reference"),
    ;

    private final String errCode;

    public DatabasirException exception() {
        return new DatabasirException(this);
    }

    public DatabasirException exception(Throwable origin) {
        return new DatabasirException(this, origin);
    }

    public DatabasirException exception(String s) {
        return new DatabasirException(this, s);
    }
}
