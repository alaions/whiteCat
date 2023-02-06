package com.chen.controller.UserController;

import com.chen.MyUtils.AppraiseUtil;
import com.chen.MyUtils.CutPage;
import com.chen.MyUtils.CutPageIntegration;
import com.chen.Service.adminService.*;
import com.chen.config.GetIp;
import com.chen.config.MyStaticProperties;
import com.chen.pojo.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class SimpleUserController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppraiseServise appraiseServise;

    @Getter
    @Setter
    private List<Topic> topicList;

    @Autowired
    private CutPage cutPage;

    @Autowired
    private IpService ipService;

    private HashMap<Integer, Integer> appraiseTopicMap;

    private HashMap<String, CutPage> cutPageMap;

    private int visitCount;

    @PostConstruct
    public void init(){
        //cutPage.setEveryPageCount(MyStaticProperties.everyPageTopicCount);
        appraiseTopicMap = new HashMap<>();
        topicList = topicService.getTopicList(); /*这里是共享了文章列表，后期可考虑算法推送*/
        cutPageMap = new HashMap<>();
    }


    @GetMapping("/userIndex.html")
    public String userIndex(Model model, HttpSession session, HttpServletRequest req){


        /*TimeZone time = TimeZone.getTimeZone("Etc/GMT-8");  //转换为中国时区
        TimeZone.setDefault(time);*/

        ipService.addIp(GetIp.getIpAddress(req), new Date());
        visitCount = ipService.getIpCount();

        appraiseTopicMap = AppraiseUtil.appraise(session, 0, appraiseTopicMap, appraiseServise, "topic");
        topicList = topicService.getTopicList();
        model.addAttribute("topicList", this.cutPage.getLimitList(topicList, cutPageMap.get(session.getId())));
        model.addAttribute("cutPage", cutPageMap.get(session.getId()));
        model.addAttribute("topTopicList", topicService.getTopBrowseTopic(MyStaticProperties.topicRankCount));//排行topic
        model.addAttribute("hotComment", userService.getHotCommentUser(MyStaticProperties.hotUserCount));//热评
        model.addAttribute("NearTimeTopic", topicService.getNearTimeTopic(MyStaticProperties.topicNearCount));//最近topic
        model.addAttribute("appraiseTopicMap", appraiseTopicMap);
        model.addAttribute("visitCount", visitCount);
        return "user/userIndex";
    }

    @GetMapping("/toUserIndex")
    public String toUserIndex(HttpSession session){

        /*为每个用户分配一个分页对象*/
        if (!cutPageMap.containsKey(session.getId())){
            cutPage.putPageInMap(cutPageMap, session, topicService);
        }

        // 每次回来都要将页码归1，且更新list，区别于select后的list
        cutPageMap.get(session.getId()).setNowPage(1);
        cutPageMap.get(session.getId()).setTotalCount(topicService.getTotalTopicCount());

        return "redirect:/userIndex.html";
    }

    @GetMapping("/index/nextPage")
    public String nextPage(HttpSession session){
        CutPageIntegration.nextPage(cutPageMap.get(session.getId()));
        return "redirect:/userIndex.html";
    }

    @GetMapping("/index/lastPage")
    public String lastPage(HttpSession session){
        CutPageIntegration.lastPage(cutPageMap.get(session.getId()));
        return "redirect:/userIndex.html";
    }

    @GetMapping("/index/toWhichPage/{page}")
    public String toWhichPage(@PathVariable("page") Integer page, HttpSession session){
        CutPageIntegration.toWhichPage(page, cutPageMap.get(session.getId()));
        return "redirect:/userIndex.html";
    }


}
