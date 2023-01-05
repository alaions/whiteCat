package com.chen.controller.UserController;

import com.chen.MyUtils.AppraiseUtil;
import com.chen.MyUtils.CutPage;
import com.chen.MyUtils.CutPageIntegration;
import com.chen.MyUtils.UnreadUtil;
import com.chen.Service.adminService.*;
import com.chen.config.MyStaticProperties;
import com.chen.pojo.Fans;
import com.chen.pojo.Notification;
import com.chen.pojo.Topic;
import com.chen.pojo.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
public class PersonalController {

    @Autowired
    private FansService fansService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CutPage cutPage;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppraiseServise appraiseServise;

    private String message; //控制个人空间主要展示内容

    private List<Topic> topicList;

    private List<User> fansList;

    private List<User> followList;

    private List<Notification> supportList;

    private List<User> chatList;

    private HashMap<Integer, Integer> appraiseTopicMap;

    private HashMap<String, CutPage> cutPageMap;

    @PostConstruct
    public void init(){
        cutPage.setEveryPageCount(MyStaticProperties.everyPageTopicCount);
        appraiseTopicMap = new HashMap<>();
        cutPageMap = new HashMap<>();
    }

    @GetMapping("personal.html")
    public String personal(Model model, HttpSession session){

        User loginUser = (User)session.getAttribute("loginUser");
        /*或者全部用session来存*/
        topicList = topicService.getTopicListByUserId(loginUser.getId());
        fansList = fansService.getFansByIdolId(loginUser.getId());
        followList = fansService.getIdolByFansId(loginUser.getId());
        supportList = notificationService.getSupportByToId(loginUser.getId());
        chatList = notificationService.getChatUser(loginUser.getId());

        if ("topic".equals(session.getAttribute("message"))){
            model.addAttribute("topicList", cutPage.getLimitList(topicList, cutPageMap.get(session.getId())));
        }
        if ("fans".equals(session.getAttribute("message"))){
            model.addAttribute("fansList", cutPage.getLimitList(fansList, cutPageMap.get(session.getId())));
        }
        if ("follow".equals(session.getAttribute("message"))){
            model.addAttribute("followList", cutPage.getLimitList(followList, cutPageMap.get(session.getId())));
        }
        if ("support".equals(session.getAttribute("message"))){
            model.addAttribute("supportList", cutPage.getLimitList(supportList, cutPageMap.get(session.getId())));
        }
        if ("chat".equals(session.getAttribute("message"))){
            model.addAttribute("chatList", cutPage.getLimitList(chatList, cutPageMap.get(session.getId())));
            model.addAttribute("userChatMap", notificationService.getUserChatCountInit(loginUser.getId(), chatList));
        }
        model.addAttribute("cutPage", cutPageMap.get(session.getId()));
        //model.addAttribute("message", message);
        model.addAttribute("messageMap", UnreadUtil.getUnreadCount(notificationService, loginUser.getId()));
        model.addAttribute("appraiseTopicMap", AppraiseUtil.appraise(session, 0, appraiseTopicMap, appraiseServise, "topic"));
        model.addAttribute("totalMessage", notificationService.getTotalUnread(loginUser.getId()));

        return "user/personal";
    }

    @GetMapping("/toPersonal/{message}")
    public String toPersonal(HttpSession session, @PathVariable("message") String message){


        //this.message = message;
        session.setAttribute("message", message);

        User loginUser = (User)session.getAttribute("loginUser");
        User user = userService.getUserById(loginUser.getId());
        session.setAttribute("loginUser", user);

        /*为每个用户分配一个分页对象*/
        if (!cutPageMap.containsKey(session.getId())){
            cutPage.putPageInMap(cutPageMap, session, topicService);
        }

        cutPageMap.get(session.getId()).setNowPage(1);
        String sessionId = session.getId();
        if("topic".equals(message)){
            cutPageMap.get(session.getId()).setEveryPageCount(MyStaticProperties.everyPageTopicCount);
            cutPageMap.get(session.getId()).setTotalCount(topicService.getTopicListByUserIdCount(loginUser.getId()));
            //topicList = topicService.getTopicListByUserId(loginUser.getId());
        }
        if("fans".equals(message)){
            cutPageMap.get(sessionId).setEveryPageCount(MyStaticProperties.showFansCount);
            cutPageMap.get(sessionId).setTotalCount(fansService.getFansByIdolIdCount(loginUser.getId()));
            //fansList = fansService.getFansByIdolId(loginUser.getId());
        }
        if("follow".equals(message)){
            cutPageMap.get(sessionId).setEveryPageCount(MyStaticProperties.showFansCount);
            cutPageMap.get(sessionId).setTotalCount(fansService.getIdolByFansIdCount(loginUser.getId()));
            //followList = fansService.getIdolByFansId(loginUser.getId());
        }
        if("support".equals(message)){
            cutPageMap.get(sessionId).setEveryPageCount(MyStaticProperties.showMessageCount);
            cutPageMap.get(sessionId).setTotalCount(notificationService.getSupportByToIdCount(loginUser.getId()));
            //supportList = notificationService.getSupportByToId(loginUser.getId());
        }
        if("chat".equals(message)){
            cutPageMap.get(sessionId).setEveryPageCount(MyStaticProperties.showMessageCount);
            cutPageMap.get(sessionId).setTotalCount(notificationService.getChatUser(loginUser.getId()).size());
            //supportList = notificationService.getSupportByToId(loginUser.getId());
        }

        return "redirect:/personal.html";
    }




    @GetMapping("/personal/nextPage")
    public String nextPage(HttpSession session){
        CutPageIntegration.nextPage(cutPageMap.get(session.getId()));
        return "redirect:/personal.html";
    }

    @GetMapping("/personal/lastPage")
    public String lastPage(HttpSession session){
        CutPageIntegration.lastPage(cutPageMap.get(session.getId()));
        return "redirect:/personal.html";
    }

    @GetMapping("/personal/toWhichPage/{page}")
    public String toWhichPage(@PathVariable("page") Integer page, HttpSession session){
        CutPageIntegration.toWhichPage(page, cutPageMap.get(session.getId()));
        return "redirect:/personal.html";
    }

    @GetMapping("/deleteTopic/{topicId}/{index}")
    @ResponseBody
    public String deleteTopicById(@PathVariable("topicId") Integer topicId, @PathVariable("index") Integer index, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)){
            return "error";
        }

        topicList.remove(index.intValue());
        topicService.deleteTopicById(topicId);
        userService.topicCountMinus(loginUser.getId());
        session.setAttribute("loginUser", userService.getUserById(loginUser.getId()));

        return "success";
    }

    @GetMapping("/detailChat/{fromId}")
    public String detailChat(@PathVariable("fromId") Integer fromId, HttpSession session, Model model){
        User loginUser = (User) session.getAttribute("loginUser");
        List<Notification> detailChat = notificationService.getDetailChat(fromId, loginUser.getId());
        notificationService.readSomeOneChat(loginUser.getId(), fromId);
        model.addAttribute("detailChat", detailChat);
        model.addAttribute("user", userService.getUserById(fromId));
        return "user/detailChat";
    }
}
