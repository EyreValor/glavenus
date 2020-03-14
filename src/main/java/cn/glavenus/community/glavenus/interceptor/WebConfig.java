package cn.glavenus.community.glavenus.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaked by EyreValor on 2020/3/7
 */
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    SesstonInterceptor sesstonInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //定义白名单
        List<String> patterns = new ArrayList<>();
        patterns.add("js/**");
        patterns.add("css/**");
        patterns.add("fonts/**");

        //设置拦截
        registry.addInterceptor(sesstonInterceptor).addPathPatterns("/**").excludePathPatterns(patterns);
    }

}