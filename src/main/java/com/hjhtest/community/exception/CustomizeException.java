package com.hjhtest.community.exception;

/**
 * @ClassName: CostomizeException
 * @Description:
 * @author: hjh
 * @date: 2022/2/26 10:56
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;


    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
