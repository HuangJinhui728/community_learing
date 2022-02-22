package com.hjhtest.community.service;

import com.hjhtest.community.data_transfer_object.PaginationDTO;
import com.hjhtest.community.data_transfer_object.QuestionDTO;
import com.hjhtest.community.mapper.QuestionMapper;
import com.hjhtest.community.mapper.UserMapper;
import com.hjhtest.community.model.Question;
import com.hjhtest.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Questionservice
 * @Description: 组装question+user,中间层
 * @author: hjh
 * @date: 2022/2/20 21:49
 */
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;




    public PaginationDTO list(Integer page, Integer size) {

        Integer offset = size*(page-1);
        Integer totalcount = questionMapper.count();

        if(offset>=totalcount){
            offset = size*(totalcount/size);
        }

        if(page<1){
            page =1;
            offset =0;
        }



        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();



        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);



        paginationDTO.setPagination(totalcount,page,size);



        return paginationDTO;
    }
}
