package com.hjhtest.community.controller;

import com.hjhtest.community.data_transfer_object.QuestionDTO;
import com.hjhtest.community.model.Question;
import com.hjhtest.community.model.User;
import com.hjhtest.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;
import java.util.HashSet;

/**
 * @ClassName: PublishController
 * @Description:
 * @author: hjh
 * @date: 2022/2/20 14:31
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.getByID(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());




        return "publish";
    }





    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }










    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required =false ) String tag,
            @RequestParam(value = "id",required = false) Long id,
            HttpServletRequest request,
            Model model){

        model.addAttribute("tittle",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title.equals("")){
            model.addAttribute("error","??????????????????");
            return "publish";
        }
        if(description == null || description.equals("")){
            model.addAttribute("error","????????????????????????");
            return "publish";
        }
        if(tag == null || tag.equals("")){
            model.addAttribute("error","??????????????????");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error","???????????????");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);
        questionService.createOrUpdate(question);
//        questionMapper.create(question);
        return "redirect:/";

    }
}
