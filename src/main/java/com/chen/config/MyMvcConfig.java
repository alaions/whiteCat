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

    /*https://blog.csdn.net/weixin_44690195/article/details/108855892*/
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // windows
        /*registry.addResourceHandler("/images/avatar/**").
                addResourceLocations(MyStaticProperties.avatar);
        registry.addResourceHandler("/images/topicPicture/**").
                addResourceLocations(MyStaticProperties.topicPicture);
        registry.addResourceHandler("/images/other/**").
                addResourceLocations(MyStaticProperties.otherPicture);*/

        //  /images/** 映射到哪里去,前端里面的路由      file:/home/fileUpload/ 表示需要访问linux系统文件所在的文件夹路径名称
        // linux的地址
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
                "/userIndex.html", "/toUserIndex","/detail/**", "/submitComment", "/reply/**", "write.html",
                "/personal.html", "/index.html", "/toArticle", "/article.html", "/category/**",
                "/article/selectSubmit", "/**/lastPage", "/**/nextPage", "/**/toWhichPage/**",
                "/topic/supportOrCriticism/**", "/comment/supportOrCriticism/**", "/register/**",
                "/toUserPage", "/user.html", "/searchName", "/otherPersonal.html", "/toOtherPersonal/**",
                "/searchTag", "/submitComment/**", "/toOtherPersonalPrivate/**", "/code.html");
    }
}
