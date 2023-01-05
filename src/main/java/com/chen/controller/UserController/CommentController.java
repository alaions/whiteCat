package com.chen.controller.UserController;

import com.chen.Service.adminService.CommentService;
import com.chen.Service.adminService.TopicService;
import com.chen.Service.adminService.UserService;
import com.chen.pojo.Comment;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@Controller
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/submitComment/{topicId}")
    @ResponseBody
    public String submitComment(@PathVariable("topicId") Integer topicId ,Comment comment, HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        if(Objects.isNull(loginUser)){
            return "请先点击右上方登录！";
        }
        if(StringUtils.isEmpty(comment.getContent())){
            return "评论不能为空！";
        }
        comment.setCommentUserId(loginUser.getId());
        comment.setCommentTime(new Date());
        comment.setFloor(commentService.getMaxParentCommentFloor(comment.getCommentTopicId()) + 1);
        commentService.insertComment(comment);
        userService.commentCountPlus(loginUser.getId());
        topicService.OneParamUpdate(topicId, "comment", 1);

        return "评论成功！";
    }

    @GetMapping("/submitReply")
    public String submitReply(Comment comment, HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        comment.setCommentUserId(loginUser.getId());
        comment.setCommentTime(new Date());
        comment.setChildFloor(commentService.getMaxChildCommentFloor(comment.getCommentTopicId(), comment.getFloor()) + 1);
        commentService.insertComment(comment);
        System.out.println("=============================================");
        userService.commentCountPlus(loginUser.getId());
        System.out.println("=============================================");
        return "redirect:detail/" + comment.getCommentTopicId();
    }

    @GetMapping("/reply/{reply}/{CommentTopicId}/{floor}/{username}")
    public String reply(Comment comment, Model model, @PathVariable("username") String username, HttpSession session, RedirectAttributes attributes){
        Object loginUser = session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            attributes.addFlashAttribute("message", "请先点击右上方登录！");
            return "redirect:/detail/" + comment.getCommentTopicId();
        }
        model.addAttribute("comment", comment);
        model.addAttribute("name", username);
        return "user/reply";
    }
}
