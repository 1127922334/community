package com.zkz.jinjiang1.provide;

import com.alibaba.fastjson.JSON;
import com.zkz.jinjiang1.DateTransmission.Access;
import com.zkz.jinjiang1.DateTransmission.GithubUserData;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//将当前类初始化到Spring的上下文
@Component
public class Github {
    public  String getAccess(Access access){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(access));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                    String string = response.body().string();
                    System.out.println(string);
                    String access_toke = string.split("&")[0].split("=")[1];
                    return access_toke;
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
    }
    public GithubUserData getUser(String AccessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+AccessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
          GithubUserData githubUserData = JSON.parseObject(string,GithubUserData.class);
          return githubUserData;
        } catch (IOException e) {
        }
        return null;
    }
}
