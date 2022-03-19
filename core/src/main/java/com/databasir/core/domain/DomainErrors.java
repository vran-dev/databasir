package com.databasir.core.domain;

import com.databasir.common.DatabasirErrors;
import com.databasir.common.DatabasirException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DomainErrors implements DatabasirErrors {
    REFRESH_TOKEN_EXPIRED("X_0001", "refresh token expired"),
    INVALID_REFRESH_TOKEN_OPERATION("X_0002", "invalid refresh token operation"),
    NETWORK_ERROR("X_0003", "网络似乎不稳定，请稍后再试"),

    NOT_SUPPORT_DATABASE_TYPE("A_10000", "不支持的数据库类型, 请检查项目配置"),
    PROJECT_NOT_FOUND("A_10001", "项目不存在"),
    DATABASE_META_NOT_FOUND("A_10002", "获取数据库信息失败"),
    CONNECT_DATABASE_FAILED("A_10003", "连接数据库失败，请检查连接配置"),
    GROUP_OWNER_MUST_NOT_BE_EMPTY("A_10004", "请至少指定一个分组组长"),
    PASSWORD_MUST_NOT_BE_BLANK("A_10005", "密码不能为空"),
    USERNAME_OR_EMAIL_DUPLICATE("A_10006", "用户名或邮箱已存在"),
    USER_ROLE_DUPLICATE("A_10007", "用户角色已存在"),
    PROJECT_NAME_DUPLICATE("A_10008", "项目名称已被占用"),
    CANNOT_UPDATE_SELF_ROLE("A_10009", "无法对自己执行角色变更的操作"),
    UPDATE_PASSWORD_CONFIRM_FAILED("A_10010", "两次密码输入不一致"),
    ORIGIN_PASSWORD_NOT_CORRECT("A_10011", "原密码不正确"),
    INVALID_CRON_EXPRESSION("A_10012", "不合法的 cron 表达式"),
    REGISTRATION_ID_DUPLICATE("A_10013", "应用注册 ID 不能重复"),
    REGISTRATION_ID_NOT_FOUND("A_10014", "应用 ID 不存在"),
    MISS_REQUIRED_PARAMETERS("A_10015", "缺少必填参数"),
    DATABASE_TYPE_NAME_DUPLICATE("A_10016", "数据库类型名已存在"),
    MUST_NOT_MODIFY_SYSTEM_DEFAULT_DATABASE_TYPE("A_10017", "禁止修改系统默认数据库类型"),
    DOWNLOAD_DRIVER_ERROR("A_10018", "驱动下载失败"),
    INVALID_DATABASE_TYPE_URL_PATTERN("A_10019", "不合法的 url pattern"),
    DOCUMENT_VERSION_IS_INVALID("A_10020", "文档版本不合法"),
    ;

    private final String errCode;

    private final String errMessage;

    public DatabasirException exception() {
        return new DatabasirException(this);
    }

    public DatabasirException exception(Throwable origin) {
        return new DatabasirException(this, origin);
    }

    public DatabasirException exception(String message, Throwable origin) {
        return new DatabasirException(this, message, origin);
    }

    public DatabasirException exception(String s) {
        return exception(s, null);
    }
}
