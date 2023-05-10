package com.chen.controller.adminController;

import com.chen.MyUtils.CutPage;
import com.chen.Service.adminService.TopicService;
import com.chen.pojo.Select;
import com.chen.pojo.Topic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private CutPage cutPage;

    @Autowired
    private Select select;

    @Setter
    @Getter
    private List<Topic> topicList;

    @GetMapping("/topicList")
    public String topicList(Model model){
        // 分页信息
        cutPage.setNowPage(1);
        // 将展示数量恢复
        cutPage.setEveryPageCount(CutPage.EVERYPAGECOUNT);
        // 用户总量
        cutPage.setTotalCount(topicService.getTotalTopicCount());

        // 搜索框为空
        select.setSelectMessage("");
        // 将展示数量恢复
        select.setShowCount(CutPage.EVERYPAGECOUNT);
        // 分页展示判断
        topicList = topicService.getTopicList();
        model.addAttribute("topicList", cutPage.getLimitList(topicList));
        // 更新要展示的list
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("select", select);

        return "admin/topic/topic-list";
    }


    //将本类中的cutPage的nowPage++后重定向
    @GetMapping("/nextPage")
    public String nextPage(Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if (cutPage.getNowPage() != cutPage.getPageCount()){
            cutPage.setNowPage(cutPage.getNowPage() + 1);
        }
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("select", select);
        model.addAttribute("topicList", cutPage.getLimitList(topicList));
        return "admin/topic/topic-list";
    }

    @GetMapping("/selectSubmit")
    // selectMessage不为空，则是正常搜索，为空则是空搜索或者是nextPage重定向回来
    public String selectSubmit(Select viewSelect, Model model){
        // 直接select = viewSelect会将showCount归0
        int showCount = select.getShowCount();
        select = viewSelect;
        select.setShowCount(showCount);
        // 每次搜索前将页码回调成1，避免List溢出
        cutPage.setNowPage(1);
        // 设置搜索后的总页数
        cutPage.setTotalCount(topicService.getTopicByWhichCount(select));
        // 更新要展示的list
        //topicList = topicService.getTopicByWhich(select);
        topicList = topicService.getTopicAndUserByWhich(select);

        model.addAttribute("topicList", cutPage.getLimitList(topicList));
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);

        return "admin/topic/topic-list";
    }

    @GetMapping("/lastPage")
    public String lastPage(Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if(cutPage.getNowPage() != 1){
            cutPage.setNowPage(cutPage.getNowPage() - 1);
        }
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("select", select);
        model.addAttribute("topicList", cutPage.getLimitList(topicList));
        return "admin/topic/topic-list";
    }

    @GetMapping("/toWhichPage/{page}")
    public String toWhichPage(@PathVariable("page") Integer page, Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if (page > cutPage.getPageCount()){
            cutPage.setNowPage(cutPage.getPageCount());
        } else {
            cutPage.setNowPage(page);
        }
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("select", select);
        model.addAttribute("topicList", cutPage.getLimitList(topicList));
        return "admin/topic/topic-list";
    }

    @GetMapping("/deleteTopic/{id}")
    public String memberList(@PathVariable("id") Integer id){
        topicService.deleteTopicById(id);
        return "redirect:/topic/topicList";
    }

    @GetMapping("/updateShowCount/{showCount}")
    public String updateShowCount(@PathVariable("showCount") int showCount, Model model){
        cutPage.setNowPage(1);
        cutPage.setEveryPageCount(showCount);
        select.setShowCount(showCount);
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("topicList", cutPage.getLimitList(topicList));
        return "admin/topic/topic-list";
    }
}
