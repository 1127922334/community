package com.zkz.jinjiang1.controller;

import com.zkz.jinjiang1.DateTransmission.Access;
import com.zkz.jinjiang1.DateTransmission.GithubUserData;
import com.zkz.jinjiang1.provide.Github;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.SocketHandler;

//信息传输
@Controller
public class AuthorizeController {
    @Autowired
    private Github github;

    @Value("${github.client.id}")
    private String id;
    @Value("${github.client.Client_secret}")
    private String Client_secret;
    @Value("${github.Redirect_uri}")
    private String Redirect_uri;

    @GetMapping("/gitlogin")
    public String back(@RequestParam(name = "code") String code,@RequestParam(name = "state") String state){
        Access access=new Access();
        access.setCode(code);
        access.setClient_id(id);
        access.setState(state);
        access.setRedirect_uri(Redirect_uri);
        access.setClient_secret(Client_secret);
        String access_tokes = github.getAccess(access);
        GithubUserData githubUserData = github.getUser(access_tokes);
        System.out.println(githubUserData.getAvatar_url());

        return "index";
    }

}
