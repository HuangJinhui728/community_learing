package com.hjhtest.community.controller;

import com.hjhtest.community.data_transfer_object.CommentDTO;
import com.hjhtest.community.data_transfer_object.QuestionDTO;
import com.hjhtest.community.enums.CommentTypeEnum;
import com.hjhtest.community.service.CommentService;
import com.hjhtest.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @ClassName: QuestionController
 * @Description:
 * @author: hjh
 * @date: 2022/2/23 21:39
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){

        QuestionDTO questionDTO = questionService.getByID(id);

        List<QuestionDTO> relatedQuestion = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestion);
        return "question";
    }
}
