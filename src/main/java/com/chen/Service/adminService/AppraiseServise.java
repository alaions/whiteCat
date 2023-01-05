package com.chen.Service.adminService;

import com.chen.MyUtils.NotificationUtil;
import com.chen.mapper.AppraiseMapper;
import com.chen.pojo.Appraise;
import com.chen.pojo.Notification;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppraiseServise implements AppraiseMapper {

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Override
    public void support(Appraise appraise) {
        appraiseMapper.support(appraise);
    }

    @Override
    public void criticism(Appraise appraise) {
        appraiseMapper.criticism(appraise);
    }

    @Override
    public List<Appraise> getAppraiseList(Integer userId, Integer topicId, int type) {
        return appraiseMapper.getAppraiseList(userId, topicId, type);
    }

    @Override
    public Appraise getAppraise(Appraise appraise) {
        return appraiseMapper.getAppraise(appraise);
    }


    @Override
    public void updateStatus(Integer id, Integer status) {
        appraiseMapper.updateStatus(id, status);
    }





}
