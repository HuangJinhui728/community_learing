package com.hjhtest.community.mapper;

import com.hjhtest.community.model.Question;
import com.hjhtest.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);


}