package com.chen.controller.adminController;

import com.chen.MyUtils.CutPage;

import com.chen.Service.adminService.TagService;
import com.chen.pojo.Select;
import com.chen.pojo.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private CutPage cutPage;

    @Autowired
    private Select select;

    @Setter
    @Getter
    private List<Tag> tagList;

    @GetMapping("/tagList")
    public String topicList(Model model){
        // 分页信息
        cutPage.setNowPage(1);
        // 将展示数量恢复
        cutPage.setEveryPageCount(CutPage.EVERYPAGECOUNT);
        // 总量
        cutPage.setTotalCount(tagService.getTotalTagCount());

        // 搜索框为空
        select.setSelectMessage("");
        // 将展示数量恢复
        select.setShowCount(CutPage.EVERYPAGECOUNT);
        // 分页展示判断
        tagList = tagService.getTagList();
        model.addAttribute("tagList", cutPage.getLimitList(tagList));

        // 更新要展示的list
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("select", select);

        return "admin/tag/tagList";
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
        cutPage.setTotalCount(tagService.getTagListByFuzzyName(viewSelect.getSelectMessage()).size());
        // 更新要展示的list
        tagList = tagService.getTagListByFuzzyName(viewSelect.getSelectMessage());

        model.addAttribute("tagList", cutPage.getLimitList(tagList));

        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);

        return "admin/tag/tagList";
    }


    @GetMapping("/nextPage")
    public String nextPage(Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if (cutPage.getNowPage() != cutPage.getPageCount()){
            cutPage.setNowPage(cutPage.getNowPage() + 1);
        }
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("tagList", cutPage.getLimitList(tagList));
        return "admin/tag/tagList";
    }


    @GetMapping("/lastPage")
    public String lastPage(Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if(cutPage.getNowPage() != 1){
            cutPage.setNowPage(cutPage.getNowPage() - 1);
        }
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("tagList", cutPage.getLimitList(tagList));
        return "admin/tag/tagList";
    }

    @GetMapping("/toWhichPage/{page}")
    public String toWhichPage(@PathVariable("page") Integer page, Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if (page > cutPage.getPageCount()){
            cutPage.setNowPage(cutPage.getPageCount());
        } else {
            cutPage.setNowPage(page);
        }
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("tagList", cutPage.getLimitList(tagList));
        return "admin/tag/tagList";
    }

    @GetMapping("/updateShowCount/{showCount}")
    public String updateShowCount(@PathVariable("showCount") int showCount, Model model){
        cutPage.setNowPage(1);
        cutPage.setEveryPageCount(showCount);
        select.setShowCount(showCount);
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("tagList", cutPage.getLimitList(tagList));
        return "admin/tag/tagList";
    }

    @GetMapping("/toUpdate/{id}")
    public String updateTag(@PathVariable("id") Integer id, Model model){

        model.addAttribute("tag", tagService.getTagById(id));

        return "admin/tag/updateTag";
    }

    @GetMapping("/submitUpdate")
    @ResponseBody
    public String submitUpdate(Integer id, String newName){
        tagService.summitUpdate(id, newName);

        return "修改成功!";
    }

    @GetMapping("/addTag")
    public String toAddTag(){
        return "admin/tag/addTag";
    }

    @GetMapping("/submitAdd")
    @ResponseBody
    public String submitAdd(String newName){
        tagService.insertTag(newName);
        return "增加成功!";
    }

    @GetMapping("/deleteTag/{id}")
    public String deleteTag(@PathVariable("id") Integer id){
        Tag tag = tagService.getTagById(id);
        int topicCount = Integer.parseInt(tag.getTopicCount());

        if (topicCount == 0){
            tagService.deleteById(id);
            return "redirect:/tag/success";
        }
        else {
            return "redirect:/tag/errorDelete";
        }
    }

    @GetMapping("/errorDelete")
    @ResponseBody
    public String errorDelete(){
        return "存在使用该标签的文章，禁止删除该标签！";
    }

    @GetMapping("/success")
    @ResponseBody
    public String success(){
        return "删除成功！";
    }

}
