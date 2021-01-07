package com.wzh.home.entity.result;

import lombok.Data;

import java.io.Serializable;

import com.wzh.home.enums.ResultCode;

/**
 * <p>
 * 基础结果返回类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 15:52
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    private Integer code = ResultCode.SUCCESS.getCode();

    /**
     * msg
     */
    private String message = ResultCode.SUCCESS.getMessage();

    /**
     * 时间戳
     */
    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
