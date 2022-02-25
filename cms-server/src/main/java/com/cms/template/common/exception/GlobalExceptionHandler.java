package com.cms.template.common.exception;

import com.cms.template.common.result.CodeMsg;
import com.cms.template.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.crypto.BadPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, BizException e) {
        logger.error("BadCredentialsException the way is：{}，reason:{}--{}", req.getServletPath(), e.getMessage(), e.toString());
        return new Result(e.getErrorCode(), e.getErrorMsg(), CodeMsg.EXCEPTION_BizException);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, BadCredentialsException e) {
        logger.error("BadCredentialsException the way is：{}，reason:{}--{}", req.getServletPath(), e.getMessage(), e.toString());
        return Result.error(CodeMsg.EXCEPTION_BadCredentialsException);
    }

    @ExceptionHandler(value = BadPaddingException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, BadPaddingException e) {
        logger.error("BadPaddingException the way is：{}，reason:{}--{}", req.getServletPath(), e.getMessage(), e.toString());
        return Result.error(CodeMsg.EXCEPTION_BadPaddingException);
    }


//    @ExceptionHandler(value = NullPointerException.class)
//    @ResponseBody
//    public Result exceptionHandler(HttpServletRequest req, NullPointerException e) {
//        logger.error("空指针异常，the way is：{}，reason:{}--{}", req.getServletPath(), e.getMessage(), e.toString());
//        return Result.error(CodeMsg.EXCEPTION_NullPointerException);
//    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, SQLIntegrityConstraintViolationException e) {
        logger.error("数据库字段参数重复，the way is：{}，reason:{}--{}", req.getServletPath(), e.getMessage(), e.toString());
        return Result.error(CodeMsg.EXCEPTION_SQLIntegrityConstraintViolationException);
    }

//    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
//    @ResponseBody
//    public ResultModel exceptionHandler(HttpServletRequest req, InternalAuthenticationServiceException e) {
//        logger.error("InternalAuthenticationServiceException the way is：{}，reason:{}--{}", req.getServletPath(), e.getMessage(), e.toString());
//        return ResultModel.exception(CommonEnum.LOGIN_ERROR, null);
//    }


//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public ResultModel exceptionHandler(HttpServletRequest req, Exception e) {
//        logger.error("Exception the way is：{}，reason:{}--{}", req.getServletPath(), e.getMessage(), e.toString());
//        return ResultModel.exception(CommonEnum.UNKNOW_ERROR, null);
//    }
}