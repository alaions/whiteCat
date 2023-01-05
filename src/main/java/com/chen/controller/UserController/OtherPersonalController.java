package com.chen.controller.UserController;

import com.chen.MyUtils.AppraiseUtil;
import com.chen.MyUtils.CutPage;
import com.chen.MyUtils.CutPageIntegration;
import com.chen.Service.adminService.AppraiseServise;
import com.chen.Service.adminService.FansService;
import com.chen.Service.adminService.TopicService;
import com.chen.Service.adminService.UserService;
import com.chen.config.MyStaticProperties;
import com.chen.pojo.Fans;
import com.chen.pojo.Topic;
import com.chen.pojo.User;
import lombok.Getter;
import lombok.Setter;
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
public class OtherPersonalController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private CutPage cutPage;

    @Autowired
    private UserService userService;

    @Autowired
    private FansService fansService;

    @Autowired
    private AppraiseServise appraiseServise;

    private List<User> fansList;

    private List<User> followList;

    private HashMap<Integer, Integer> appraiseTopicMap;

    private HashMap<String, CutPage> cutPageMap;

    @Getter
    @Setter
    private List<Topic> topicList;

    //private User user;

    @PostConstruct
    public void init(){
        cutPage.setEveryPageCount(MyStaticProperties.everyPageTopicCount);
        appraiseTopicMap = new HashMap<>();
        cutPageMap = new HashMap<>();
    }

    /*基本所有数据都存储在类属性中，集中在这个mapper中传递*/
    @GetMapping("/otherPersonal.html")
    public String otherPersonal(Model model, HttpSession session){

        if ("topic".equals(session.getAttribute("otherMessage"))){
            model.addAttribute("topicList", cutPage.getLimitList(topicList, cutPageMap.get(session.getId())));
        }
        else if ("fans".equals(session.getAttribute("otherMessage"))){
            model.addAttribute("fansList", cutPage.getLimitList(fansList, cutPageMap.get(session.getId())));
        }
        else if ("follow".equals(session.getAttribute("otherMessage"))){
            model.addAttribute("followList", cutPage.getLimitList(followList, cutPageMap.get(session.getId())));
        }
        else {
            session.setAttribute("otherMessage", "topic");
            model.addAttribute("topicList", cutPage.getLimitList(topicList, cutPageMap.get(session.getId())));
        }

        model.addAttribute("cutPage", cutPageMap.get(session.getId()));
        model.addAttribute("topicList", cutPage.getLimitList(topicList, cutPageMap.get(session.getId())));
        model.addAttribute("appraiseTopicMap", AppraiseUtil.appraise(session, 0, appraiseTopicMap, appraiseServise, "topic"));

        return "user/otherPersonal";
    }

    @GetMapping("/toOtherPersonal/{id}")
    public String toPersonal(@PathVariable("id") Integer id, HttpSession session){

        System.out.println(session.getId()+session.getAttribute("otherMessage"));

        User loginUser = (User) session.getAttribute("loginUser");
        // 当跳转空间是自己的情况
        if (Objects.nonNull(loginUser)){
            if (id == loginUser.getId()){
                return "redirect:/toPersonal/topic";
            }
        }

        /*为每个用户分配一个分页对象*/
        if (!cutPageMap.containsKey(session.getId())){
            cutPage.putPageInMap(cutPageMap, session, topicService);
        }

        cutPageMap.get(session.getId()).setNowPage(1);
        //user = userService.getUserById(id);
        topicList = topicService.getTopicListByUserId(id);
        cutPageMap.get(session.getId()).setTotalCount(topicService.getTopicListByUserIdCount(id));

        if (Objects.nonNull(loginUser)){
            //判断是否已经关注了这个人
            session.setAttribute("ifFans", Objects.nonNull(fansService.ifIAmFans(loginUser.getId(), id)));
        }

        session.setAttribute("user", userService.getUserById(id));
        session.setAttribute("otherMessage", "topic");

        //user = userService.getUserById(id);

        return "redirect:/otherPersonal.html";
    }

    @GetMapping("/toOtherPersonalPrivate/{otherMessage}")
    public String toPersonal(HttpSession session, @PathVariable("otherMessage") String otherMessage){

        session.setAttribute("otherMessage", otherMessage);

        cutPageMap.get(session.getId()).setNowPage(1);
        User user = (User) session.getAttribute("user");

        int id = user.getId();
        if("fans".equals(otherMessage)){
            cutPageMap.get(session.getId()).setEveryPageCount(MyStaticProperties.showFansCount);
            cutPageMap.get(session.getId()).setTotalCount(fansService.getFansByIdolIdCount(id));
            fansList = fansService.getFansByIdolId(id);
        }
        if("follow".equals(otherMessage)){
            cutPageMap.get(session.getId()).setEveryPageCount(MyStaticProperties.showFansCount);
            cutPageMap.get(session.getId()).setTotalCount(fansService.getIdolByFansIdCount(id));
            followList = fansService.getIdolByFansId(id);
        }
        if ("topic".equals(otherMessage)){
            return "redirect:/toOtherPersonal/" + id;
        }

        return "redirect:/otherPersonal.html";
        //return "redirect:/toOtherPersonal/" + id;
    }

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable("id") Integer id ,Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "user/chat";
    }

    @GetMapping("/otherPersonal/nextPage")
    public String nextPage(HttpSession session){
        CutPageIntegration.nextPage(cutPageMap.get(session.getId()));
        return "redirect:/otherPersonal.html";
    }

    @GetMapping("/otherPersonal/lastPage")
    public String lastPage(HttpSession session){
        CutPageIntegration.lastPage(cutPageMap.get(session.getId()));
        return "redirect:/otherPersonal.html";
    }

    @GetMapping("/otherPersonal/toWhichPage/{page}")
    public String toWhichPage(@PathVariable("page") Integer page, HttpSession session){
        CutPageIntegration.toWhichPage(page, cutPageMap.get(session.getId()));
        return "redirect:/otherPersonal.html";
    }

}
