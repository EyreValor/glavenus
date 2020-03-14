package cn.glavenus.community.glavenus.service.impl;

import cn.glavenus.community.glavenus.dto.AccessTokenDTO;
import cn.glavenus.community.glavenus.dto.GithubUser;
import cn.glavenus.community.glavenus.provider.GithubProvider;
import cn.glavenus.community.glavenus.service.IAuthorizeService;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Creaked by EyreValor on 2020/3/2
 */
@Service
public class AuthorizeServiceImpl implements IAuthorizeService {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    @Override
    public String getAccessToken(String code, String state) throws IOException {
        //封装DTO
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        //获取accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        if (false){
            //TODO 规划获取失败异常
        }
        return accessToken;
    }

    @Override
    public GithubUser getGithubUser(String accessToken) {
        //获取githubUser
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if (false){
            //TODO 规划获取失败异常
        }
        return githubUser;
    }
}
