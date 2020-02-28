package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.AccessTokenDTO;
import cn.glavenus.community.glavenus.dto.GithubUser;
import cn.glavenus.community.glavenus.mapper.UserMapper;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Creaked by EyreValor on 2020/2/25
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        //封装DTO
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        //获取token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //获取githubUser
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if (githubUser != null){
            //查询数据库是否有创建账户
            User user = userMapper.findByUser(String.valueOf(githubUser.getId()));
            if(user==null){
                //如果数据库中没有创建账户
                user = new User();
                user.setName(githubUser.getName());
                user.setAccountId(String.valueOf(githubUser.getId()));
                String setToken = UUID.randomUUID().toString();
                //将cookie写入数据库持久保存
                user.setToken(setToken);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                //将用户数据写入数据库
                userMapper.insert(user);
                //自定义cookie
                Cookie token = new Cookie("token", setToken);
                token.setMaxAge(60*60*24*7);//设置cookie时间为7天
                response.addCookie(token);
                return "redirect:/";
            }else{
                //如果有创建账户  设置全新token写入账户
                String setToken = UUID.randomUUID().toString();
                int i = userMapper.updateToken(setToken,String.valueOf(githubUser.getId()));
                user.setToken(setToken);
                Cookie token = new Cookie("token", setToken);
                token.setMaxAge(60*60*24*7);
                response.addCookie(token);
                request.getSession().setAttribute("user", user);
                return "redirect:/";
            }
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}
