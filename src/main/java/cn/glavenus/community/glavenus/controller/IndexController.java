package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.mapper.UserMapper;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creaked by EyreValor on 2020/2/25
 */
@Controller
public class IndexController {

    @Autowired
    private IUserService userServiceimpl;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        userServiceimpl.setLoginStatus(request);
        return "index";
    }
}
