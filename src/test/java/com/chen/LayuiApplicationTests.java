package com.chen;

import com.chen.MyUtils.CheckUtil;
import com.chen.MyUtils.CutPage;
import com.chen.MyUtils.NotificationUtil;
import com.chen.MyUtils.TimeUtil;
import com.chen.Service.adminService.*;
import com.chen.mapper.*;
import com.chen.pojo.Comment;
import com.chen.pojo.Select;
import com.chen.pojo.Topic;
import com.chen.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class LayuiApplicationTests {

    @Autowired
    SqlSession session;

    @Autowired
    IpMapper ipMapper;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    @Autowired
    CommentService commentService;


    @Test
    public void test(){

        int ifMain = commentService.IfMainComment(133);

        System.out.println(ifMain);

    }


}
