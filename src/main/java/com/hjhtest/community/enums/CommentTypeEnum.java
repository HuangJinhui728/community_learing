package com.hjhtest.community.enums;

import com.hjhtest.community.model.Comment;

/**
 * @ClassName: CommentTypeEnum
 * @Description:
 * @author: hjh
 * @date: 2022/3/18 19:54
 */
public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;

    }
}
