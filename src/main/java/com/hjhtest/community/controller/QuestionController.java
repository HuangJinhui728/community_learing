package com.hjhtest.community.controller;

import com.hjhtest.community.data_transfer_object.QuestionDTO;
import com.hjhtest.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){

        QuestionDTO questionDTO = questionService.getByID(id);

        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
