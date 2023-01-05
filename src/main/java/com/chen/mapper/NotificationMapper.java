package com.chen.mapper;

import com.chen.pojo.Notification;
import com.chen.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface NotificationMapper {

    void insertSupportNotification(Notification notification);

    void insertChar(Notification notification);

    List<Notification> getSupportByToId(Integer id);

    Integer getSupportByToIdCount(Integer id);

    Integer getTotalUnread(Integer id);

    Integer getSupportUnread(Integer id);

    Integer commentUnread(Integer id);

    Integer fansUnread(Integer id);

    Integer messageUnread(Integer id);

    Integer systemUnread(Integer id);

    void updateStatus(Integer id);

    void updateSomeStatus(@Param("userId") Integer userId, @Param("message") String message);

    Integer getTotalChatCount(Integer toId); //总私信数

    List<User> getChatUser(Integer toId); //有未读消息的发送人

    Integer getChatCountById(Integer toId); //每个用户发来的未读消息数

    Integer getUserChatCount(@Param("toId") Integer toId, @Param("fromId") Integer fromId);

    void readSomeOneChat(@Param("toId") Integer toId, @Param("fromId") Integer fromId);

    List<Notification> getDetailChat(@Param("fromId") Integer fromId, @Param("toId") Integer toId);//详细私信内容
}
