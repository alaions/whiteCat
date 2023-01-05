package com.chen.MyUtils;

import com.chen.Service.adminService.TopicService;
import com.chen.config.MyStaticProperties;
import com.chen.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CutPage {

    //默认
    public final static int EVERYPAGECOUNT = 5;

    //每页记录数
    private int everyPageCount = EVERYPAGECOUNT;

    //总记录数
    private int totalCount;

    //当前页
    private int nowPage = 1;

    //上一页
    private int lastPage;

    //下一页
    private int nextPage;

    //当前页的第一条记录的位次
    private int nowFirst;

    //当前页的最后一条记录的位次
    private int nowLast;

    //页数
    private int PageCount;

    public CutPage(int everyPageCount) {
        this.everyPageCount = everyPageCount;
    }

    public int getNowFirst(){
        return (nowPage - 1) * everyPageCount;
    }

    public int getNowLast(){
        return getNowFirst() + everyPageCount - 1;
    }

    public int getLastPage(){
        return nowPage - 1;
    }

    public int getNextPage(){
        return nowPage + 1;
    }

    public int getTotalCount(){
        return totalCount;
    }

    public int getPageCount(){
        if (getTotalCount() == 0){
            return 1;
        }
        if (getTotalCount() % getEveryPageCount() == 0){
            return getTotalCount() / everyPageCount;
        }
        return getTotalCount() / everyPageCount + 1;
    }

    // 分页判断
    public List getLimitList(List List){
        List alist;
        if (this.getNowLast() >= List.size()){
            alist = List.subList(this.getNowFirst(), List.size());
        } else {
            alist = List.subList(this.getNowFirst(), this.getNowLast() + 1);
        }
        return alist;
    }

    public List getLimitList(List List, CutPage cutPage){
        List alist;
        if (cutPage.getNowLast() >= List.size()){
            alist = List.subList(cutPage.getNowFirst(), List.size());
        } else {
            alist = List.subList(cutPage.getNowFirst(), cutPage.getNowLast() + 1);
        }
        return alist;
    }

    /*为后面进来的用户分配一个cutPage*/
    public void putPageInMap(HashMap<Integer, CutPage> cutPageMap, User loginUser, TopicService topicService){

        int id = loginUser.getId();

        CutPage cutPage = new CutPage();
        cutPage.setEveryPageCount(MyStaticProperties.everyPageTopicCount);
        cutPage.setNowPage(1);
        cutPage.setTotalCount(topicService.getTotalTopicCount());

        cutPageMap.put(id, cutPage);
    }

    public void putPageInMap(HashMap<String, CutPage> cutPageMap, HttpSession session, TopicService topicService){

        String id = session.getId();

        CutPage cutPage = new CutPage();
        cutPage.setEveryPageCount(MyStaticProperties.everyPageTopicCount);
        cutPage.setNowPage(1);
        cutPage.setTotalCount(topicService.getTotalTopicCount());

        cutPageMap.put(id, cutPage);
    }

}
