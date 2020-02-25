package cn.glavenus.community.glavenus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Creaked by EyreValor on 2020/2/25
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){return "index";}
}
