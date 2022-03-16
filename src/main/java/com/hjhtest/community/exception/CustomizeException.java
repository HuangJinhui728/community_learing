package com.hjhtest.community.exception;

/**
 * @ClassName: CostomizeException
 * @Description:
 * @author: hjh
 * @date: 2022/2/26 10:56
 */
public class CustomizeException extends RuntimeException{
    private String message;


    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }




    public CustomizeException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
