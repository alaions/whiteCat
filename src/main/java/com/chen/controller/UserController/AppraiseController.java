package com.chen.controller.UserController;

import com.chen.MyUtils.NotificationUtil;
import com.chen.Service.adminService.*;
import com.chen.pojo.Appraise;
import com.chen.pojo.Notification;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class AppraiseController {

    @Autowired
    private AppraiseServise appraiseServise;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    //在detail的ajax
    @GetMapping("/topic/supportOrCriticism/{topicId}")
    @ResponseBody
    public String supportOrCriticism(@PathVariable("topicId") Integer topicId, HttpSession session){
        int support = 0;
        User loginUser = (User)session.getAttribute("loginUser");
        if(Objects.isNull(loginUser)){
            return "error";
        }
        Appraise appraise = appraiseServise.getAppraise(new Appraise(0, loginUser.getId(), topicId, 1, 0, 0));
        if (Objects.isNull(appraise)){
            appraiseServise.support(new Appraise(0, loginUser.getId(), topicId, 1, 0, 1));
            topicService.OneParamUpdate(topicId, "support", 1);

            topicSupportUtil(loginUser, topicId); //**********

        } else {
            /*原来是取消点赞的就重新点赞，原来没点赞的就点赞，合在一起，status 0/1变化*/
            appraiseServise.updateStatus(appraise.getId(), appraise.getStatus());

            if (appraise.getStatus() == 1){
                support = -1;
                cancelTopicSupportUtil(loginUser, topicId); //***********
            } else {
                support = 1;
                topicSupportUtil(loginUser, topicId);  //***********
            }
            topicService.OneParamUpdate(topicId, "support", support);
        }
        session.setAttribute("loginUser", userService.getUserById(loginUser.getId()));

        return String.valueOf(topicService.getTopicById(topicId).getSupportCount());
    }


    @GetMapping("/comment/supportOrCriticism/{topicId}/{commentId}")
    @ResponseBody
    public String supportOrCriticismComment(@PathVariable("topicId") Integer topicId, @PathVariable("commentId") Integer commentId, HttpSession session){
        int support = 0;
        User loginUser = (User)session.getAttribute("loginUser");
        if(Objects.isNull(loginUser)){
            return "error";
        }
        Appraise appraise = appraiseServise.getAppraise(new Appraise(0, loginUser.getId(), topicId, 0, commentId, 0));
        if (Objects.isNull(appraise)){
            appraiseServise.support(new Appraise(0, loginUser.getId(), topicId, 0, commentId, 1));
            commentService.updateSupportCount(commentId, 1);

            //notificationService.supportNotification(loginUser.getId(), commentService.getUserIdByCommentId(commentId), topicId, commentId); // 插入通知表
            commentSupportUtil(loginUser, topicId, commentId);
        } else {
            /*原来是取消点赞的就重新点赞，原来没点赞的就点赞，合在一起，status 0/1变化*/
            appraiseServise.updateStatus(appraise.getId(), appraise.getStatus());

            if (appraise.getStatus() == 1){
                support = -1;
                //notificationService.cancelSupportNotification(loginUser.getId(), commentService.getUserIdByCommentId(commentId), topicId, commentId);// 插入通知表
                cancelCommentSupportUtil(loginUser, topicId, commentId);
            } else {
                support = 1;
                //notificationService.supportNotification(loginUser.getId(), commentService.getUserIdByCommentId(commentId), topicId, commentId); // 插入通知表
                commentSupportUtil(loginUser, topicId, commentId);
            }
            commentService.updateSupportCount(commentId, support);
        }
        session.setAttribute("loginUser", userService.getUserById(loginUser.getId()));

        return "success";
    }

    public void topicSupportUtil(User loginUser, Integer topicId){
        notificationService.supportNotification(loginUser.getId(), topicService.getUserIdByTopicId(topicId), topicId, 0); // 插入通知表
        userService.supportCountPlus(topicService.getUserIdByTopicId(topicId));
        //topicService.OneParamUpdate(topicId, "support", 1);
    }

    public void cancelTopicSupportUtil(User loginUser, Integer topicId){
        notificationService.cancelSupportNotification(loginUser.getId(), topicService.getUserIdByTopicId(topicId), topicId, 0); // 插入通知表
        userService.supportCountMinus(topicService.getUserIdByTopicId(topicId));
        //topicService.OneParamUpdate(topicId, "support", -1);
    }

    public void commentSupportUtil(User loginUser, Integer topicId, Integer commentId){
        notificationService.supportNotification(loginUser.getId(), commentService.getUserIdByCommentId(commentId), topicId, commentId);
        userService.supportCountPlus(commentService.getUserIdByCommentId(commentId));
        topicService.OneParamUpdate(topicId, "support", 1);
    }

    public void cancelCommentSupportUtil(User loginUser, Integer topicId, Integer commentId){
        notificationService.cancelSupportNotification(loginUser.getId(), commentService.getUserIdByCommentId(commentId), topicId, commentId);
        userService.supportCountMinus(commentService.getUserIdByCommentId(commentId));
        topicService.OneParamUpdate(topicId, "support", 1);
    }

}
