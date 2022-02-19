package com.hjhtest.community.controller;

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
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name") String name , Model model){
        model.addAttribute("name",name);
        return "hello";
    }

}
