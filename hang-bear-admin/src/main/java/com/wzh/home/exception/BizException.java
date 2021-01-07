package com.wzh.home.exception;

import lombok.Data;

/**
 * <p>
 * 业务异常类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 16:20
 */
@Data
public class BizException extends RuntimeException {

    /**
     * 异常编码
     */
    private Integer code;

    public BizException() {
        super();
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(Integer code) {
        super();
        this.code = code;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
