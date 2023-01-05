package com.chen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "index.static.properties")
//@Data
public class MyStaticProperties {

    public static String avatar; //头像的路径

    public static String topicPicture; //帖子封面的路径

    public static String otherPicture; //其他图片的路径

    public static String myEmail;

    public static int tagCount; //分类导航的上的类别显示数量

    public static int everyPageTopicCount; //首页每页的topic数量

    public static int topicRankCount; //topic热度排行的数量

    public static int topicNearCount; //topic最新发布榜的数量

    public static int hotUserCount; //热评用户榜的数量

    public static int showFansCount; //空间里一页展示的粉丝数量

    public static int showMessageCount; //展示信息的数量

    public static int getShowMessageCount() {
        return showMessageCount;
    }

    public void setShowMessageCount(int showMessageCount) {
        MyStaticProperties.showMessageCount = showMessageCount;
    }

    public static int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        MyStaticProperties.tagCount = tagCount;
    }

    public static String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        MyStaticProperties.avatar = avatar;
    }

    public static String getTopicPicture() {
        return topicPicture;
    }

    public void setTopicPicture(String topicPicture) {
        MyStaticProperties.topicPicture = topicPicture;
    }

    public static String getOtherPicture() {
        return otherPicture;
    }

    public void setOtherPicture(String otherPicture) {
        MyStaticProperties.otherPicture = otherPicture;
    }

    public static String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        MyStaticProperties.myEmail = myEmail;
    }

    public static int getShowFansCount() {
        return showFansCount;
    }

    public void setShowFansCount(int showFansCount) {
        MyStaticProperties.showFansCount = showFansCount;
    }

    public static int getEveryPageTopicCount() {
        return everyPageTopicCount;
    }

    public void setEveryPageTopicCount(int everyPageTopicCount) {
        MyStaticProperties.everyPageTopicCount = everyPageTopicCount;
    }

    public static int getTopicRankCount() {
        return topicRankCount;
    }

    public void setTopicRankCount(int topicRankCount) {
        MyStaticProperties.topicRankCount = topicRankCount;
    }

    public static int getTopicNearCount() {
        return topicNearCount;
    }

    public void setTopicNearCount(int topicNearCount) {
        MyStaticProperties.topicNearCount = topicNearCount;
    }

    public static int getHotUserCount() {
        return hotUserCount;
    }

    public void setHotUserCount(int hotUserCount) {
        MyStaticProperties.hotUserCount = hotUserCount;
    }
}
