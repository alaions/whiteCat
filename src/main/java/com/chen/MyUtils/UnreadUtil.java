package com.chen.MyUtils;

import com.chen.Service.adminService.NotificationService;

import java.util.HashMap;

public class UnreadUtil {

    private int totalUnread; // 未读消息总数
    private int supportUnread; //未读点赞消息数
    private int commentUnread; //未读评论消息数
    private int fansUnread; //新增粉丝未读消息数
    private int messageUnread; //私信未读消息数
    private int systemUnread; //未读系统消息数

    public static HashMap<String, Integer> getUnreadCount(NotificationService notificationService, Integer userId){

        HashMap<String, Integer> map = new HashMap<>();

        map.put("totalUnread", notificationService.getTotalUnread(userId));
        map.put("supportUnread", notificationService.getSupportUnread(userId));
        map.put("chatUnread", notificationService.getTotalChatCount(userId));

        return map;
    }
}
