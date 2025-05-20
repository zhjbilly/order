package com.qizhi.order.util;

/**
 * 业务异常
 */
public class BizException extends RuntimeException{

    private String errorCode;
    private String message;

    /**
     * 获取errorCode的值。
     *
     * @return 返回errorCode的值。
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置errorCode的值。
     *
     * @param errorCode 要设置的errorCode的值。
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取message的值。
     *
     * @return 返回message的值。
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置message的值。
     *
     * @param message 要设置的message的值。
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 构造方法。
     *
     * @param message 错误消息。
     */
    public BizException(String message) {
        super();
        this.message = message;
    }

    /**
     * 构造方法。
     *
     * @param errorCode 错误码。
     * @param message 错误消息。
     */
    public BizException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

}
