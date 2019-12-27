package com.wushiyii.template.springboottemplateserver.common.aspect;

import com.wushiyii.template.springboottemplatebase.common.dict.ErrorCode;
import com.wushiyii.template.springboottemplatebase.exception.BizRuntimeException;
import com.wushiyii.template.springboottemplatebase.exception.InvalidParamException;
import com.wushiyii.template.springboottemplateserver.vo.BaseDataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BaseDataVO defaultExceptionHandler(Exception e) {
        logger.error("error", e);
        return buildServerErrorVO(ErrorCode.SERVICE_INTERNAL_ERROR.getCode(), ErrorCode.SERVICE_INTERNAL_ERROR.getDesc());
    }

    @ResponseBody
    @ExceptionHandler(BizRuntimeException.class)
    public BaseDataVO bizRuntimeExceptionHandler(BizRuntimeException e) {
        return buildServerErrorVO(e.getStatusCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidParamException.class)
    public BaseDataVO invalidParamExceptionHandler(InvalidParamException e) {
        return buildClientErrorVO(e.getStatusCode(),  e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageConversionException.class)
    public BaseDataVO httpMessageExceptionHandler(HttpMessageConversionException e) {
        logger.warn("Could not convert http message. {}", e.getMessage());
        return buildClientErrorVO(ErrorCode.BAD_REQUEST_ERROR.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseDataVO httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        return buildClientErrorVO(ErrorCode.BAD_REQUEST_ERROR.getCode(), e.getMessage());
    }

    public static BaseDataVO buildServerErrorVO(int errorCode, String message) {
        return buildBaseData(ErrorCode.SERVICE_INTERNAL_ERROR.getCode(), errorCode, message);
    }

    public static BaseDataVO buildClientErrorVO(int errorCode, String message) {
        return buildBaseData(ErrorCode.BAD_REQUEST_ERROR.getCode(), errorCode, message);
    }

    public static BaseDataVO buildBaseData(int httpCode, int errorCode, String message){
        BaseDataVO result = new BaseDataVO();

        result.setCode(httpCode);
        result.setData(null);
        result.setMessage(message);
        return result;
    }


}
