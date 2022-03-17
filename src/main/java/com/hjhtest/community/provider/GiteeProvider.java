package com.hjhtest.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjhtest.community.data_transfer_object.AccessTokenDTO;
import com.hjhtest.community.data_transfer_object.AccessTokenGiteeDTO;
import com.hjhtest.community.data_transfer_object.GiteeUserDTO;
import com.hjhtest.community.data_transfer_object.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName: GiteeProvider
 * @Description:
 * @author: hjh
 * @date: 2022/3/17 22:19
 */
@Component
public class GiteeProvider {

    public String getAccessToken(AccessTokenGiteeDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = JSONObject.parseObject(string);
            //分割得到access_token
            String token  = jsonObject.getString("access_token");
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }


    public GiteeUserDTO getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("https://gitee.com/api/v5/user"))
                .newBuilder();
        urlBuilder.addQueryParameter("access_token", accessToken);
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();


        try {
            Response response = client.newCall(request).execute();
            String string = Objects.requireNonNull(response.body()).string();
            GiteeUserDTO giteeUserDTO = JSON.parseObject(string, GiteeUserDTO.class);
            return giteeUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
