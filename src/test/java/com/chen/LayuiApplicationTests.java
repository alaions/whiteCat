package com.chen;

import com.chen.MyUtils.*;
import com.chen.Service.adminService.*;
import com.chen.config.Trie;
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
import org.springframework.util.StringUtils;

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

    @Autowired
    ControlTrie controlTrie;


    @Test
    public void test(){
        for (String word : controlTrie.getSensitiveTrie().getList()) {
            if (StringUtils.hasText(word)){
                controlTrie.getSensitiveTrie().insert(word);
            }
        }
        String comment = "哈台湾哈";
        boolean ifBad = controlTrie.getSensitiveTrie().searchWord(comment);
        if (ifBad){
            System.out.println("此评论包含敏感词！");
        }else {
            System.out.println("无");
        }


    }


}
