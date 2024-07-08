package com.yangrr.center.common;

/**
 * @PATH com.yangrr.center.common.ResultUtils
 * @Author YangRR
 * @CreateData 2024-07-02 13:47
 * @Description 简化处理结果时都要调用BaseResponse  将成功失败的可能都封装到这里
 */
public class ResultUtils {
    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(20000, data, "success");
    }
    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
    /**
     * 失败
     *
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }
    /**
     * 失败 - 消息和描述
     *
     * @param errorCode
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message,description);
    }
    /**
     * 失败 - 描述无消息
     *
     * @param errorCode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(), null,errorCode.getMessage(), description);
    }
}
