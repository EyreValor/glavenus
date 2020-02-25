package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.AccessTokenDTO;
import cn.glavenus.community.glavenus.dto.GithubUser;
import cn.glavenus.community.glavenus.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Creaked by EyreValor on 2020/2/25
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("Iv1.6c4f6f2053e11699");
        accessTokenDTO.setClient_secret("ba9dbe4705abe4268ec8d27563dcdca703c68f9b");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        return "index";
    }
}
