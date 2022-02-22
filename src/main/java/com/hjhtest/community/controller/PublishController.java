package com.hjhtest.community.controller;

import com.hjhtest.community.mapper.QuestionMapper;
import com.hjhtest.community.mapper.UserMapper;
import com.hjhtest.community.model.Question;
import com.hjhtest.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: PublishController
 * @Description:
 * @author: hjh
 * @date: 2022/2/20 14:31
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required =false ) String tag,
            HttpServletRequest request,
            Model model){

        model.addAttribute("tittle",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || description.equals("")){
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if(tag == null || tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user = null;
        Cookie[] cookies =request.getCookies();
        if(cookies != null && cookies.length !=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user =userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";

    }
}
