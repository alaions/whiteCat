package com.chen.MyUtils;

import com.chen.Service.adminService.CommentService;
import com.chen.Service.adminService.NotificationService;
import com.chen.Service.adminService.TopicService;
import com.chen.Service.adminService.UserService;
import com.chen.pojo.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class NotificationUtil extends Notification {

    private int fromId;
    private int toId;
    private String fromName;
    private String toName;
    private String title;  //消息的title
    private String content; //消息的内容
    private Date time;
    private int topicId;
    private int commentId;
    private String topicTitle;
    private String commentContent;
    private String str;
    private int subject;
    private String chatContent;
    private UserService userService;
    private TopicService topicService;
    private CommentService commentService;

    public void setNeed(int fromId, int toId, int topicId, int commentId, UserService userService, TopicService topicService, CommentService commentService){
        this.setFromId(fromId);
        this.setToId(toId);
        this.setTopicId(topicId);
        this.setCommentId(commentId);
        this.setUserService(userService);
        this.setTopicService(topicService);
        this.setCommentService(commentService);
        this.setAll();
    }

    public void setAll(){
        this.setFromName(userService.getUserById(fromId).getUsername());
        this.setToName(userService.getUserById(toId).getUsername());
        this.setTime(new Date());
        this.setTopicTitle(topicService.getTopicById(topicId).getTitle());
        if(commentId != 0){
            this.setCommentContent(commentService.getCommentById(commentId).getContent());
        }
    }

    public void setChatNeed(int fromId, int toId, String content ,UserService userService){
        this.setFromId(fromId);
        this.setToId(toId);
        this.setChatContent(content);
        this.setUserService(userService);
        setChatAll();
    }

    public void setChatAll(){
        if (fromId != 0){
            this.setFromName(userService.getUserById(fromId).getUsername());
        } else {
            this.setFromName("游客");
        }
        this.setToName(userService.getUserById(toId).getUsername());
        this.setTime(new Date());
        this.setSubject(4);
        this.setContent("<span style=\"color: #0e8c8c\">" + fromName + "</span>" + "给你发来一条私信");
    }

    /*文章点赞*/
    public NotificationUtil supportTopic(){
        this.setSubject(2);
        this.setTitle("帖子点赞通知");
        this.setContent(fromName + "于" + time.toString() + "点赞了你的文章‘<span style=\"color: #0e8c8c\">" + topicTitle + "</span>‘");
        return this;
    }

    /*取消文章点赞*/
    public NotificationUtil cancelSupportTopic(){
        this.setSubject(2);
        this.setTitle("帖子取消点赞通知");
        this.setContent(fromName + "于" + time + "取消了对你文章’<span style=\"color: #0e8c8c\">" + topicTitle + "</span>’的点赞");
        return this;
    }

    /*评论点赞*/
    public NotificationUtil supportComment(){
        this.setSubject(2);
        this.setTitle("评论点赞通知");
        this.setContent(fromName + "于" + time.toString() + "点赞了你在文章'" + topicTitle + "'下发表的评论：‘<span style=\"color: #0e8c8c\">" + commentContent + "</span>’");
        return this;
    }

    /*取消评论点赞*/
    public NotificationUtil cancelSupportComment(){
        this.setSubject(2);
        this.setTitle("评论取消点赞通知");
        this.setContent(fromName + "于" + time + "取消了你在文章'" + topicTitle + "'下发表的评论：‘<span style=\"color: #0e8c8c\">" + commentContent + "</span>‘的点赞");
        return this;
    }


}
