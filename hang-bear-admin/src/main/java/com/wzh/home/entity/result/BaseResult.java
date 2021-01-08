package com.wzh.home.entity.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.wzh.home.enums.ResultCode;

/**
 * <p>
 * 基础结果返回类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/7 15:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResult<T> extends Result {

    private static final long serialVersionUID = 1L;

    private T data;

    public BaseResult() {
        super();
    }

    public BaseResult(T data) {
        super();
        this.data = data;
    }

    public BaseResult(ResultCode resultCode, T data) {
        super(resultCode);
        this.data = data;
    }

    public BaseResult(Integer code, String message) {
        super(code, message);
    }

    public BaseResult(ResultCode resultCode) {
        super(resultCode);
    }

    public static BaseResult<?> success() {
        return new BaseResult<>(ResultCode.SUCCESS);
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<T>(data);
    }

    public static BaseResult<?> success(Integer code, String message) {
        return new BaseResult<>(code, message);
    }

    public static BaseResult<?> fail(ResultCode resultCode) {
        return new BaseResult<>(resultCode);
    }

    public static <T> BaseResult<T> fail(T data) {
        return new BaseResult<T>(ResultCode.FAIL, data);
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
