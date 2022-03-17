package com.hjhtest.community.controller;

import com.hjhtest.community.data_transfer_object.AccessTokenDTO;
import com.hjhtest.community.data_transfer_object.AccessTokenGiteeDTO;
import com.hjhtest.community.data_transfer_object.GiteeUserDTO;
import com.hjhtest.community.data_transfer_object.GithubUserDTO;
import com.hjhtest.community.model.User;
import com.hjhtest.community.provider.GiteeProvider;
import com.hjhtest.community.provider.GithubProvider;
import com.hjhtest.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    @Autowired
    private GiteeProvider giteeProvider;

    @Value("${github.client.id}")
    private String githubClientId;
    @Value("${github.client.secret}")
    private String githubClientSecret;
    @Value("${github.redirect.uri}")
    private String githubRedirectUri;


    @Value("${gitee.client.id}")
    private String giteeClientId;
    @Value("${gitee.client.secret}")
    private String giteeClientSecret;
    @Value("${gitee.redirect.uri}")
    private String giteeRedirectUri;


    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(githubClientId);
        accessTokenDTO.setClient_secret(githubClientSecret);
        accessTokenDTO.setRedirect_uri(githubRedirectUri);
        accessTokenDTO.setCode(code);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);

        if((githubUserDTO != null) && (githubUserDTO.getId() != null)){

            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            user.setLognet(0);
            user.setAvatarUrl(githubUserDTO.getAvatar_url());
            userService.createOrUpDate(user);

            response.addCookie(new Cookie("token",token));
            //登陆成功 写cookie 和 session
            return "redirect:/";
        } else{
            //登陆失败，重新登录
            return "redirect:/";
        }

    }





    @GetMapping("/callbackgitee")
    public String callbackgitee(@RequestParam(name="code") String code,
                           HttpServletResponse response){

        AccessTokenGiteeDTO accessTokenGiteeDTO = new AccessTokenGiteeDTO();
        accessTokenGiteeDTO.setClient_id(giteeClientId);
        accessTokenGiteeDTO.setClient_secret(giteeClientSecret);
        accessTokenGiteeDTO.setRedirect_uri(giteeRedirectUri);
        accessTokenGiteeDTO.setCode(code);

        String accessToken = giteeProvider.getAccessToken(accessTokenGiteeDTO);
        GiteeUserDTO giteeUserDTO = giteeProvider.getUser(accessToken);

        if((giteeUserDTO != null) && (giteeUserDTO.getId() != null)){

            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(giteeUserDTO.getName());
            user.setAccountId(String.valueOf(giteeUserDTO.getId()));
            user.setLognet(1);
            user.setAvatarUrl(giteeUserDTO.getAvatar_url());
            userService.createOrUpDate(user);

            response.addCookie(new Cookie("token",token));
            //登陆成功 写cookie 和 session
            return "redirect:/";
        } else{
            //登陆失败，重新登录
            return "redirect:/";
        }

    }









    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }



}
