package com.cms.template.common.exception;

import com.cms.template.common.result.CodeMsg;

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * error code
     */
    protected Integer errorCode;
    /**
     * error message
     */
    protected String errorMsg;

    public BizException() {
        super();
    }

    public BizException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.errorCode = codeMsg.getCode();
        this.errorMsg = codeMsg.getMsg();
    }

    public BizException(CodeMsg codeMsg, Throwable cause) {
        super(codeMsg.getMsg(), cause);
        this.errorCode = codeMsg.getCode();
        this.errorMsg = codeMsg.getMsg();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BizException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}