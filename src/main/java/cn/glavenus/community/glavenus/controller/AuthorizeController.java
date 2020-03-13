package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.GithubUser;
import cn.glavenus.community.glavenus.mapper.UserMapper;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.IAuthorizeService;
import cn.glavenus.community.glavenus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IAuthorizeService authorizeServiceimpl;

    @Autowired
    private IUserService userServiceimpl;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        //获取accessToken
        String accessToken = authorizeServiceimpl.getAccessToken(code, state);
        //获取GithubUser
        GithubUser githubUser = authorizeServiceimpl.getGithubUser(accessToken);

        if (githubUser != null) {
            //查询数据库是否有创建账户
            User user = userServiceimpl.getUserByAccountId(String.valueOf(githubUser.getId()));
            if (user == null) {
                String token = UUID.randomUUID().toString();
                userServiceimpl.createUser(githubUser, user, token);
                //自定义cookie
                setCookie(token, response);
                return "redirect:/";
            } else {
                //如果有创建账户  设置全新token写入账户
                String token = UUID.randomUUID().toString();
                userServiceimpl.updateToken(token, String.valueOf(githubUser.getId()));
                user.setToken(token);
                setCookie(token,response);
                request.getSession().setAttribute("user", user);
                return "redirect:/";
            }
        } else {
            //登录失败
            return "redirect:/";
        }
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                           HttpServletResponse response){
        Cookie newCookie = new Cookie("token",null);
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);
        request.getSession().removeAttribute("user");
        return "redirect:/";

    }

    //设置cookie
    private void setCookie(String setToken, HttpServletResponse response) {
        Cookie token = new Cookie("token", setToken);
        token.setMaxAge(60 * 60 * 24 * 7);//设置cookie时间为7天
        response.addCookie(token);
    }
}
