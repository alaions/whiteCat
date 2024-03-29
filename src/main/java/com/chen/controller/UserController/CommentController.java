package com.chen.controller.UserController;

import com.chen.mapper.UserMapper;
import com.chen.pojo.ResultJson;
import com.chen.MyUtils.ControlTrie;
import com.chen.Service.adminService.CommentService;
import com.chen.Service.adminService.TopicService;
import com.chen.Service.adminService.UserService;
import com.chen.config.MyStaticProperties;
import com.chen.pojo.Comment;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@Controller
public class CommentController {

    @Autowired
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ControlTrie controlTrie;

    @GetMapping("/submitComment/{topicId}")
    @ResponseBody
    public String submitComment(@PathVariable("topicId") Integer topicId ,Comment comment, HttpSession session, Model model){
        User loginUser = (User)session.getAttribute("loginUser");
        if(Objects.isNull(loginUser)){
            return "请先点击右上方登录！";
        }
        if(StringUtils.isEmpty(comment.getContent())){
            return "评论不能为空！";
        }

        boolean ifBad = controlTrie.getSensitiveTrie().searchWord(comment.getContent());
        if (ifBad){
            userMapper.updateUserBadCount(loginUser);
            int badCount = userMapper.getUserById(loginUser.getId()).getBadCount();
            if (badCount % Integer.parseInt(MyStaticProperties.banCount) == 0){
                userMapper.banUser(loginUser);
                session.removeAttribute("loginUser");
                model.addAttribute("msg", "此账号已被封禁！");
                return "此账号涉嫌违规，已被封禁！";
            }
            int i = Integer.parseInt(MyStaticProperties.banCount);
            return "此评论包含敏感词！多次发布将封禁该账号，你还剩余" + (i - badCount % i - 1) + "次机会！";
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
    @ResponseBody
    public ResultJson submitReply(Comment comment, HttpSession session){
        boolean ifBad = controlTrie.getSensitiveTrie().searchWord(comment.getContent());
        if (ifBad){
            return new ResultJson("403", "此评论包含敏感词！");
        }
        User loginUser = (User)session.getAttribute("loginUser");
        comment.setCommentUserId(loginUser.getId());
        comment.setCommentTime(new Date());
        comment.setChildFloor(commentService.getMaxChildCommentFloor(comment.getCommentTopicId(), comment.getFloor()) + 1);
        commentService.insertComment(comment);
        userService.commentCountPlus(loginUser.getId());
       // return "redirect:detail/" + comment.getCommentTopicId();
        return new ResultJson("200", "回复成功！");
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

    @GetMapping("/deleteComment/{commentId}")
    @ResponseBody
    public String deleteMainComment(@PathVariable("commentId") Integer commentId){

        int topicId = commentService.getTopicId(commentId);

        //判断是否为主楼层
        int ifMain = commentService.IfMainComment(commentId);

        //是主楼层
        if(ifMain == 1){

            //得到该评论在该文章的楼层
            int parentFloor = commentService.getFloor(commentId);
            /*没子楼层回复： 直接删*/
            int exitsChild = commentService.ifExitsChild(topicId, commentId, parentFloor);

            if(exitsChild == 0){

                commentService.deleteComment(commentId);

            }
            /*有子楼层回复   把内容屏蔽*/
            else {

                commentService.shield(MyStaticProperties.shield, commentId);

            }
        }
        //是子楼层直接删
        else {
            commentService.deleteComment(commentId);
        }

        return "删除成功";


    }


}
