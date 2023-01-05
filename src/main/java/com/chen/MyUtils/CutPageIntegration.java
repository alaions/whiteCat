package com.chen.MyUtils;

public class CutPageIntegration {

    public static CutPage nextPage(CutPage cutPage){
        if (cutPage.getNowPage() != cutPage.getPageCount()){
            cutPage.setNowPage(cutPage.getNowPage() + 1);
        }
        return cutPage;
    }

    public static CutPage lastPage(CutPage cutPage){
        if(cutPage.getNowPage() != 1){
            cutPage.setNowPage(cutPage.getNowPage() - 1);
        }
        return cutPage;
    }

    public static CutPage toWhichPage(Integer page,CutPage cutPage){
        if (page > cutPage.getPageCount()){
            cutPage.setNowPage(cutPage.getPageCount());
        } else {
            cutPage.setNowPage(page);
        }

        return cutPage;
    }

}
