package com.yangrr.center.exception;

import com.yangrr.center.common.ErrorCode;
/**
 * @PATH com.yangrr.center.exception.BusinessException
 * @Author YangRR
 * @CreateData 2024-07-02 13:40
 * @Description 自定义异常类
 */

public class BusinessException extends RuntimeException {
    private final int code;
    private final String description;

    //给原有的RuntimeException中加入 message code description
    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    //给原有的RuntimeException中加入errorCode
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}