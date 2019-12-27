package com.wushiyii.template.springboottemplatebase.exception;

import com.wushiyii.template.springboottemplatebase.common.dict.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvalidParamException extends RuntimeException {

    private Integer statusCode;

    private String message;

    public InvalidParamException(ErrorCode errorCode){
        super(errorCode.getDesc());
        this.statusCode = errorCode.getCode();
        this.message = errorCode.getDesc();
    }

    public InvalidParamException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public InvalidParamException(String message, Throwable cause, Integer statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
        this.message = message;
    }
}
