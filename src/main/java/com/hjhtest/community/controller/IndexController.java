package com.hjhtest.community.controller;

import com.hjhtest.community.data_transfer_object.PaginationDTO;
import com.hjhtest.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @ClassName: HelloController
 * @Description: hello
 * @author: hjh
 * @date: 2022/2/19 10:04
 */


@Controller
public class IndexController {



    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue= "5") Integer size){


        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);




        return "index";
    }

}
