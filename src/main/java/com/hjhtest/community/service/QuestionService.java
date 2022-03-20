package com.hjhtest.community.service;

import com.hjhtest.community.data_transfer_object.PaginationDTO;
import com.hjhtest.community.data_transfer_object.QuestionDTO;
import com.hjhtest.community.exception.CustomizeErrorCode;
import com.hjhtest.community.exception.CustomizeException;
import com.hjhtest.community.mapper.QuestionExtMapper;
import com.hjhtest.community.mapper.QuestionMapper;
import com.hjhtest.community.mapper.UserMapper;
import com.hjhtest.community.model.Question;
import com.hjhtest.community.model.QuestionExample;
import com.hjhtest.community.model.User;
import com.hjhtest.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    @Autowired
    private QuestionExtMapper questionExtMapper;



    public PaginationDTO list(Integer page, Integer size) {

        Integer offset = size*(page-1);
        Integer totalcount = (int) questionMapper.countByExample(new QuestionExample());

        if(offset>=totalcount){
            offset = size*(totalcount/size);
        }

        if(page<1){
            page =1;
            offset =0;
        }


        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();



        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
//            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);



        paginationDTO.setPagination(totalcount,page,size);



        return paginationDTO;
    }

    public PaginationDTO list(Long userID, Integer page, Integer size) {
        Integer offset = size*(page-1);


        QuestionExample questionExample = new QuestionExample();

        questionExample.createCriteria()
                        .andCreatorEqualTo(userID);
        Integer totalcount = (int) questionMapper.countByExample(questionExample);


        if(offset>=totalcount){
            offset = size*(totalcount/size);
        }

        if(page<1){
            page =1;
            offset =0;
        }


        QuestionExample questionExample2 = new QuestionExample();
        questionExample2.createCriteria()
                .andCreatorEqualTo(userID);


        questionExample2.setOrderByClause("gmt_create desc");

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample2,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            User user = users.get(0);


            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalcount,page,size);

        return paginationDTO;
    }

    public QuestionDTO getByID(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);

        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);


        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);


        return questionDTO;

    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }
        else{
            //更新

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }

    public void incView(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);


    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }

        String[] tags =  StringUtils.split(queryDTO.getTag(),",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(question1 -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question1,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());


        return questionDTOS;
    }
}
