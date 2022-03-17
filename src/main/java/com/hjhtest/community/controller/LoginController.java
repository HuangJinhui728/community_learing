package com.hjhtest.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: LoginController
 * @Description:
 * @author: hjh
 * @date: 2022/3/17 21:37
 */

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){


        return "login";

    }
}
