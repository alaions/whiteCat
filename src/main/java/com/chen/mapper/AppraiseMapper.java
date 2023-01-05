package com.chen.mapper;

import com.chen.pojo.Appraise;
import com.chen.pojo.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AppraiseMapper {

    void support(Appraise appraise);

    void criticism(Appraise appraise);

    List<Appraise> getAppraiseList(@Param("userId") Integer userId, @Param("topicId") Integer topicId,@Param("type") int type);

    Appraise getAppraise(Appraise appraise);

    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

}
