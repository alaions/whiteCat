package com.chen.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 扩展配置类
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Value("${index.static.properties.avatar}")
    private String avatarPath;

    @Value("${index.static.properties.topicPicture}")
    private String topicPicturePath;

    public void addViewControllers(ViewControllerRegistry registry) {
        /*registry.addViewController("/main.html").setViewName("admin/adindex");*/
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/register.html").setViewName("register");
        registry.addViewController("/index.html").setViewName("redirect:toUserIndex");
        registry.addViewController("/code.html").setViewName("code");
    }

    /*增加资源索引*/
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String avatar = "file:" + avatarPath;
        String topicPicture = "file:" + topicPicturePath;
        registry.addResourceHandler("/images/avatar/**").
                addResourceLocations(avatar);
        registry.addResourceHandler("/images/topicPicture/**").
                addResourceLocations(topicPicture);
        registry.addResourceHandler("/images/other/**").
                addResourceLocations("file:/www/wwwroot/images/other/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/css/**", "/font-awesome/**",
                "/fonts/**", "/images/**", "/img/**", "/js/**", "/layui/**", "/lib/**",
                "/", "/login", "/register", "/login.html", "/main.html", "/register.html",
                "/userIndex.html", "/toUserIndex","/detail/**", "/submitComment", "/reply/**",
                "/personal.html", "/index.html", "/toArticle", "/article.html", "/category/**",
                "/article/selectSubmit", "/**/lastPage", "/**/nextPage", "/**/toWhichPage/**",
                "/topic/supportOrCriticism/**", "/comment/supportOrCriticism/**", "/register/**",
                "/toUserPage", "/user.html", "/searchName", "/otherPersonal.html", "/toOtherPersonal/**",
                "/searchTag", "/submitComment/**", "/toOtherPersonalPrivate/**", "/code.html");
    }
}
