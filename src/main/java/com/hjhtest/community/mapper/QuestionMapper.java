package com.hjhtest.community.mapper;

import com.hjhtest.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: QuestionMapper
 * @Description:
 * @author: hjh
 * @date: 2022/2/20 17:00
 */
@Mapper
@Repository
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(Integer offset, Integer size);


    @Select("select count(1) from question")
    Integer count();

}
