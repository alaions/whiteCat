package com.chen;

import com.chen.MyUtils.*;
import com.chen.Service.adminService.*;
import com.chen.mapper.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;


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
