package com.wzh.home.enums;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 15:58
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(0, "success"),

    /**
     * 失败
     */
    FAIL(1, "fail"),

    ;

    /**
     * 状态码
     */
    Integer code;

    /**
     * 信息描述
     */
    String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
