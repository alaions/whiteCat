package com.chen.controller.UserController;

import com.chen.MyUtils.AppraiseUtil;
import com.chen.Service.adminService.AppraiseServise;
import com.chen.Service.adminService.CommentService;
import com.chen.Service.adminService.TopicService;
import com.chen.Service.adminService.UserService;
import com.chen.pojo.Appraise;
import com.chen.pojo.Comment;
import com.chen.pojo.Topic;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
public class DetailController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppraiseServise appraiseServise;

    private HashMap<Integer, Integer> appraiseTopicMap;

    private HashMap<Integer, Integer> appraiseCommentMap;

    @PostConstruct
    public void init(){
        appraiseTopicMap = new HashMap<>();
        appraiseCommentMap = new HashMap<>();
    }

    @GetMapping("/detail/{topicId}")
    public String detail(@PathVariable("topicId") Integer topicId, Model model, HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        List<Comment> commentList = commentService.getCommentListByTopicId(topicId, 0);
        Topic topic = topicService.getTopicById(topicId);
        User author = userService.getUserById(topic.getTopicUserId());

        /*这里应该用mybatis的resultMap来做的*/
        for (Comment comment : commentList) {
            // 装入主评论作者
            comment.setAuthor(userService.getUserById(comment.getCommentUserId()));
            // 子评论列表, 根据楼层查询
            List<Comment> childCommentList = commentService.getCommentListByTopicId(topicId, comment.getFloor());
            for (Comment child : childCommentList) {
                // 装入子评论作者
                child.setAuthor(userService.getUserById(child.getCommentUserId()));
                // 装入要发送给的对象
                child.setReplyTo(userService.getUserById(child.getReply()));
            }
            comment.setChildCommentList(childCommentList);
        }

        appraiseTopicMap = AppraiseUtil.appraise(session, topicId ,appraiseTopicMap, appraiseServise, "topic");
        appraiseCommentMap = AppraiseUtil.appraise(session, topicId, appraiseCommentMap, appraiseServise, "comment");

        topic.setCommentList(commentList);
        topic.setUser(author);
        /*浏览量增加*/
        topicService.OneParamUpdate(topicId, "browse", 1);
        userService.browseCountPlus(topicService.getUserIdByTopicId(topicId));

        if (Objects.nonNull(loginUser)){
            session.setAttribute("loginUser", userService.getUserById(loginUser.getId()));
        }

        model.addAttribute("topic", topic);
        model.addAttribute("appraiseTopicMap", appraiseTopicMap);
        model.addAttribute("appraiseCommentMap", appraiseCommentMap);

        return "user/detail";
    }

}
