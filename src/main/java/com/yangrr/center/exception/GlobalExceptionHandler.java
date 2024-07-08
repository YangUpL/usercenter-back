package com.yangrr.center.exception;

import com.yangrr.center.common.BaseResponse;
import com.yangrr.center.common.ErrorCode;
import com.yangrr.center.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @PATH com.yangrr.center.exception.GlobalExceptionHandler
 * @Author YangRR
 * @CreateData 2024-07-02 13:40
 * @Description 全局异常类
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获BusinessException异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        // 日志集中处理
        log.error("businessException:" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(),
                e.getDescription());
    }

    /**
     * 捕获RuntimeException异常
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(BusinessException e) {
        // 日志集中处理
        log.error("runtimeException:", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}