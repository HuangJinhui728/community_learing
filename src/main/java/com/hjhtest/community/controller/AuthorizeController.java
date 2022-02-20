package com.hjhtest.community.controller;

import com.hjhtest.community.data_transfer_object.AccessTokenDTO;
import com.hjhtest.community.data_transfer_object.GithubUserDTO;

import com.hjhtest.community.mapper.UserMapper;
import com.hjhtest.community.model.User;
import com.hjhtest.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @ClassName: AuthorizeController
 * @Description:
 * @author: hjh
 * @date: 2022/2/19 16:21
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);

        if(githubUserDTO != null){
            //登陆成功 写cookie 和 session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);



            request.getSession().setAttribute("user",githubUserDTO);
            return "redirect:/";
        } else{
            //登陆失败，重新登录
            return "redirect:/";
        }

    }
}
