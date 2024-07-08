package com.yangrr.center.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @PATH com.yangrr.center.common.BaseResponse
 * @Author YangRR
 * @CreateData 2024-07-02 13:40
 * @description 处理返回结果时调用的类
 */
@Data
public class BaseResponse<T> {
    private static final long serialVersionUID = -3541610571427268456L;
    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null,
                errorCode.getMessage(), errorCode.getDescription());
    }
}

