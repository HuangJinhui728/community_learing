package com.hjhtest.community.mapper;

import com.hjhtest.community.model.Comment;
import com.hjhtest.community.model.CommentExample;
import com.hjhtest.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}