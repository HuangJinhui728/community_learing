package com.hjhtest.community.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName: CustomizeErrorCode
 * @Description:
 * @author: hjh
 * @date: 2022/2/26 11:08
 */




public enum CustomizeErrorCode implements ICustomizeErrorCode{


    QUESTION_NOT_FOUND("查找到问题不存在，要不要换一个试试");

    private CustomizeErrorCode( String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String getMessage(){
        return  message;
    }


}
