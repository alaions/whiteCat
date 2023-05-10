package com.chen.Service.adminService;

import com.chen.mapper.TagMapper;
import com.chen.mapper.TopicMapper;
import com.chen.pojo.Select;
import com.chen.pojo.Tag;
import com.chen.pojo.Topic;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService implements TopicMapper{

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TagMapper tagMapper;

    public int getTotalTopicCount(){
        return topicMapper.getTotalTopicCount();
    }

    public List<Topic> getTopicList(){
        List<Topic> topicList = topicMapper.getTopicAddUserList();
        for (Topic topic : topicList) {
            topic.setTagName(tagMapper.getTagNameById(topic.getTopicTagId()));
        }
        return topicList;
    }

    @Override
    public List<Topic> getTopicAddUserList() {
        return topicMapper.getTopicAddUserList();
    }

    public List<Topic> getTopicByWhich(Select select){
        return topicMapper.getTopicByWhich(select);
    }

    @Override
    public List<Topic> getTopicAndUserByWhich(Select select) {
        return topicMapper.getTopicAndUserByWhich(select);
    }

    public int getTopicByWhichCount(Select select){
        return topicMapper.getTopicByWhichCount(select);
    }

    public void deleteTopicById(Integer id){
        topicMapper.deleteTopicById(id);
    }

    public List<Topic> getTopBrowseTopic(int count){
        return topicMapper.getTopBrowseTopic(count);
    }

    @Override
    public Integer getUserIdByTopicId(Integer topicId) {
        return topicMapper.getUserIdByTopicId(topicId);
    }

    @Override
    public void insertTopic(Topic topic) {
        topicMapper.insertTopic(topic);
    }

    @Override
    public List<Topic> getNearTimeTopic(int count) {
        return topicMapper.getNearTimeTopic(count);
    }

    @Override
    public List<Topic> getTopicListByTagId(Integer id) {
        List<Topic> topicListByTagId = topicMapper.getTopicListByTagId(id);
        for (Topic topic : topicListByTagId) {
            topic.setTagName(tagMapper.getTagNameById(topic.getTopicTagId()));
        }
        return topicListByTagId;
    }

    @Override
    public int getTopicListByTagIdCount(Integer id) {
        return topicMapper.getTopicListByTagIdCount(id);
    }

    @Override
    public void OneParamUpdate(Integer topicId, String message, int updateParam) {
        topicMapper.OneParamUpdate(topicId, message, updateParam);
    }

    public Topic getTopicById(Integer id){
        return topicMapper.getTopicById(id);
    }

    @Override
    public List<Topic> getTopicListBySelectMessage(String selectMessage) {
        List<Topic> topicList = topicMapper.getTopicListBySelectMessage(selectMessage);
        for (Topic topic : topicList) {
            topic.setTagName(tagMapper.getTagNameById(topic.getTopicTagId()));
        }
        return topicList;
    }

    @Override
    public int getTopicListBySelectMessageCount(String selectMessage) {
        return topicMapper.getTopicListBySelectMessageCount(selectMessage);
    }

    @Override
    public List<Topic> getTopicListByUserId(Integer id) {
        List<Topic> topicList = topicMapper.getTopicListByUserId(id);
        for (Topic topic : topicList) {
            topic.setTagName(tagMapper.getTagNameById(topic.getTopicTagId()));
        }
        return topicList;
    }

    @Override
    public int getTopicListByUserIdCount(Integer id) {
        return topicMapper.getTopicListByUserIdCount(id);
    }


}
