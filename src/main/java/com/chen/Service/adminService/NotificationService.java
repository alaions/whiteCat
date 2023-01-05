package com.chen.Service.adminService;

import com.chen.MyUtils.NotificationUtil;
import com.chen.mapper.NotificationMapper;
import com.chen.pojo.Notification;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService implements NotificationMapper {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CommentService commentService;

    @Override
    public void insertSupportNotification(Notification notification) {
        notificationMapper.insertSupportNotification(notification);
    }

    @Override
    public void insertChar(Notification notification) {
        notificationMapper.insertChar(notification);
    }

    @Override
    public List<Notification> getSupportByToId(Integer id) {
        return notificationMapper.getSupportByToId(id);
    }

    @Override
    public Integer getSupportByToIdCount(Integer id) {
        return notificationMapper.getSupportByToIdCount(id);
    }

    @Override
    public Integer getTotalUnread(Integer id) {
        return notificationMapper.getTotalUnread(id);
    }

    @Override
    public Integer getSupportUnread(Integer id) {
        return notificationMapper.getSupportUnread(id);
    }

    @Override
    public Integer commentUnread(Integer id) {
        return notificationMapper.commentUnread(id);
    }

    @Override
    public Integer fansUnread(Integer id) {
        return notificationMapper.fansUnread(id);
    }

    @Override
    public Integer messageUnread(Integer id) {
        return notificationMapper.messageUnread(id);
    }

    @Override
    public Integer systemUnread(Integer id) {
        return notificationMapper.systemUnread(id);
    }

    @Override
    public void updateStatus(Integer id) {
        notificationMapper.updateStatus(id);
    }

    @Override
    public void updateSomeStatus(Integer userId, String message) {
        notificationMapper.updateSomeStatus(userId, message);
    }

    @Override
    public Integer getTotalChatCount(Integer toId) {
        return notificationMapper.getTotalChatCount(toId);
    }

    @Override
    public List<User> getChatUser(Integer toId) {
        return notificationMapper.getChatUser(toId);
    }

    @Override
    public Integer getChatCountById(Integer toId) {
        return notificationMapper.getChatCountById(toId);
    }

    @Override
    public Integer getUserChatCount(Integer toId, Integer fromId) {
        return notificationMapper.getUserChatCount(toId, fromId);
    }

    @Override
    public void readSomeOneChat(Integer toId, Integer fromId) {
        notificationMapper.readSomeOneChat(toId, fromId);
    }

    public Map getUserChatCountInit(Integer toId, List<User> list){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (User user : list) {
            map.put(user.getId(), getUserChatCount(toId, user.getId()));
        }
        return map;
    }

    @Override
    public List<Notification> getDetailChat(Integer fromId, Integer toId) {
        return notificationMapper.getDetailChat(fromId, toId);
    }

    public void supportNotification(int fromId, int toId, int topicId, int commentId){
        NotificationUtil notificationUtil = new NotificationUtil();
        notificationUtil.setNeed(fromId, toId, topicId, commentId, userService, topicService, commentService);
        if (commentId == 0){
            notificationUtil.supportTopic();
        } else {
            notificationUtil.supportComment();
        }
        notificationMapper.insertSupportNotification(notificationUtil);
    }

    public void cancelSupportNotification(int fromId, int toId, int topicId, int commentId){
        NotificationUtil notificationUtil = new NotificationUtil();
        notificationUtil.setNeed(fromId, toId, topicId, commentId, userService, topicService, commentService);
        if (commentId == 0){
            notificationUtil.cancelSupportTopic();
        } else {
            notificationUtil.cancelSupportComment();
        }
        notificationMapper.insertSupportNotification(notificationUtil);
    }
}
