package com.chen.controller.UserController;

import com.chen.MyUtils.AppraiseUtil;
import com.chen.MyUtils.CutPage;
import com.chen.MyUtils.CutPageIntegration;
import com.chen.Service.adminService.AppraiseServise;
import com.chen.Service.adminService.NotificationService;
import com.chen.Service.adminService.TagService;
import com.chen.Service.adminService.TopicService;
import com.chen.config.MyStaticProperties;
import com.chen.pojo.Tag;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
public class ArticleController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private CutPage cutPage;

    @Autowired
    private TagService tagService;

    @Autowired
    private AppraiseServise appraiseServise;

    @Getter
    @Setter
    private List<Topic> topicList;

    private List<Tag> tagList;

    private HashMap<Integer, Integer> appraiseTopicMap;

    private HashMap<String, CutPage> cutPageMap;

    @PostConstruct
    public void init(){
        cutPage.setEveryPageCount(MyStaticProperties.everyPageTopicCount);
        topicList = topicService.getTopicList();
        tagList = tagService.getTagList();
        tagList = tagList.subList(0, Math.min(tagList.size(), MyStaticProperties.tagCount));
        appraiseTopicMap = new HashMap<>();
        cutPageMap = new HashMap<>();
    }

    @GetMapping("/article.html")
    public String article(Model model, HttpSession session){
        appraiseTopicMap = AppraiseUtil.appraise(session, 0, appraiseTopicMap, appraiseServise, "topic");

        model.addAttribute("topicList", cutPage.getLimitList(topicList, cutPageMap.get(session.getId())));
        model.addAttribute("cutPage", cutPageMap.get(session.getId()));
        model.addAttribute("tagList", tagList);
        model.addAttribute("appraiseTopicMap", appraiseTopicMap);

        return "user/article";
    }

    @GetMapping("/toArticle")
    public String toUserIndex(HttpSession session){


        /*为每个用户分配一个分页对象*/
        if (!cutPageMap.containsKey(session.getId())){
            cutPage.putPageInMap(cutPageMap, session, topicService);
        }

        // 每次回来都要将页码归1，且更新list，区别于select后的list
        cutPageMap.get(session.getId()).setNowPage(1);
        cutPageMap.get(session.getId()).setTotalCount(topicService.getTotalTopicCount());

        topicList = topicService.getTopicList();
        return "redirect:/article.html";
    }

    @GetMapping("/article/nextPage")
    public String nextPage(HttpSession session){
        CutPageIntegration.nextPage(cutPageMap.get(session.getId()));
        return "redirect:/article.html";
    }

    @GetMapping("/article/lastPage")
    public String lastPage(HttpSession session){
        CutPageIntegration.lastPage(cutPageMap.get(session.getId()));
        return "redirect:/article.html";
    }

    @GetMapping("/article/toWhichPage/{page}")
    public String toWhichPage(@PathVariable("page") Integer page, HttpSession session){
        CutPageIntegration.toWhichPage(page, cutPageMap.get(session.getId()));
        return "redirect:/article.html";
    }

    @GetMapping("/article/selectSubmit")
    public String selectSubmit(String selectMessage, HttpSession session){

        /*为每个用户分配一个分页对象*/
        if (!cutPageMap.containsKey(session.getId())){
            cutPage.putPageInMap(cutPageMap, session, topicService);
        }

        // 每次搜索前将页码回调成1，避免List溢出
        cutPageMap.get(session.getId()).setNowPage(1);
        // 设置搜索后的总页数
        cutPageMap.get(session.getId()).setTotalCount(topicService.getTopicListBySelectMessageCount(selectMessage));
        // 更新要展示的list
        topicList = topicService.getTopicListBySelectMessage(selectMessage);

        for (Topic topic : topicList) {
            System.out.println(topic.getUser().getUsername());
        }

        session.setAttribute("selectMessage", selectMessage);


        return "redirect:/article.html";
    }

    @GetMapping("/category/{tagId}")
    public String category(@PathVariable Integer tagId, HttpSession session){
        /*为每个用户分配一个分页对象*/
        if (!cutPageMap.containsKey(session.getId())){
            cutPage.putPageInMap(cutPageMap, session, topicService);
        }
        cutPageMap.get(session.getId()).setNowPage(1);
        cutPageMap.get(session.getId()).setTotalCount(topicService.getTopicListByTagIdCount(tagId));
        topicList = topicService.getTopicListByTagId(tagId);
        return "redirect:/article.html";
    }

    @GetMapping("/searchTag")
    @ResponseBody
    public String searchTag(String tagMessage){
        List<Tag> list = tagService.getTagListByFuzzyName(tagMessage);
        if (list.size() == 0){
            return "none";
        }else {
            tagList = list;
            return "success";
        }

    }

}
