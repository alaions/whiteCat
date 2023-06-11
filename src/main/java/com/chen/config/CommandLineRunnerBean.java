package com.chen.config;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.chen.MyUtils.ControlTrie;
import com.chen.Service.adminService.SensitiveService;
import com.chen.Service.adminService.TopicService;
import com.chen.Service.adminService.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunnerBean.class);

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private ControlTrie controlTrie;

    @Autowired
    private SensitiveService sensitiveService;

    public void run(String... args) {
        // TODO 我们自己的业务逻辑

        controlTrie.getTopicTrie().setList(topicService.getTotalTitle());
        controlTrie.getUserTrie().setList(userService.getTotalName());
        controlTrie.getSensitiveTrie().setList(sensitiveService.getSensitiveWord());

        /*controlTrie.setTopicTrie(controlTrie.getTopicTrie());
        controlTrie.setUserTrie(controlTrie.getUserTrie());
        controlTrie.setSensitiveTrie(controlTrie.getUserTrie());*/

        for (String topicTitle : controlTrie.getTopicTrie().getList()) {
            if (!StringUtils.isEmpty(topicTitle)){

                controlTrie.getTopicTrie().insertPlus(topicTitle);
            }
        }

        for (String userName : controlTrie.getUserTrie().getList()) {
            if (StringUtils.hasText(userName)){
                controlTrie.getUserTrie().insertPlus(userName);
            }
        }

        for (String word : controlTrie.getSensitiveTrie().getList()) {
            if (StringUtils.hasText(word)){
                // 不拼音
                controlTrie.getSensitiveTrie().insert(word);
            }
        }

        String strArgs = Arrays.stream(args).collect(Collectors.joining("|"));
        logger.info("Application started with arguments:" + strArgs);
    }
}
