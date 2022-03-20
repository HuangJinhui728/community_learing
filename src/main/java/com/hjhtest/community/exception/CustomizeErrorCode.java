package com.hjhtest.community.exception;

/**
 * @ClassName: CustomizeErrorCode
 * @Description:
 * @author: hjh
 * @date: 2022/2/26 11:08
 */




public enum CustomizeErrorCode implements ICustomizeErrorCode{


    QUESTION_NOT_FOUND(2001,"查找到问题不存在，要不要换一个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGION(2003,"当前操作需要登陆，请登录后重试"),
    SYSTEM_ERROR(2004,"服务器冒烟了！稍后再试试？"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你@的评论不存在"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    ;

    private CustomizeErrorCode( String message) {
        this.message = message;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage(){
        return  message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
