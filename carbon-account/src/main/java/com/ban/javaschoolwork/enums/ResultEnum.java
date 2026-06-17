package com.ban.carbonaccount.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(200, "请求成功"),
    FAIL(500, "请求失败"),
    TOKEN_ERROR(401, "Token无效或已过期"),
    AUTH_ERROR(403, "权限不足"),
    PARAM_ERROR(400, "参数校验失败"),

    // 碳账户业务相关
    POINTS_INSUFFICIENT(1001, "碳积分不足"),
    ACCOUNT_NOT_FOUND(1002, "碳账户不存在"),
    CARBON_RECORD_NOT_FOUND(1003, "碳减排记录不存在");

    private final Integer code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}