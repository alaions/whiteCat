package com.chen.Service.adminService;

import com.chen.mapper.UserMapper;
import com.chen.pojo.Select;
import com.chen.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserMapper{

    @Autowired
    UserMapper userMapper;

    public List<User> getUserList(){
        return userMapper.getUserList();
    }

    public List<User> getUserByWhich(Select select){
        return userMapper.getUserByWhich(select);
    }

    public void deleteUser(Integer id){
        userMapper.deleteUserById(id);
    }

    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }

    public void updateUserById(User user){
        userMapper.updateUserById(user);
    }

    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    @Override
    public void topicCountPlus(Integer id) {
        userMapper.topicCountPlus(id);
    }

    @Override
    public void commentCountPlus(Integer id) {
        userMapper.commentCountPlus(id);
    }

    @Override
    public void browseCountPlus(Integer id) {
        userMapper.browseCountPlus(id);
    }

    @Override
    public void supportCountPlus(Integer id) {
        userMapper.supportCountPlus(id);
    }

    @Override
    public void supportCountMinus(Integer id) {
        userMapper.supportCountMinus(id);
    }

    @Override
    public void topicCountMinus(Integer id) {
        userMapper.topicCountMinus(id);
    }

    @Override
    public List<User> getHotCommentUser(int count) {
        return userMapper.getHotCommentUser(count);
    }

    @Override
    public void updateUserLimit(User user) {
        userMapper.updateUserLimit(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public List<User> getUserListByMessage(String namMessage) {
        return userMapper.getUserListByMessage(namMessage);
    }

    @Override
    public void updateOnlyOne(Integer id, String value, String message) {
        userMapper.updateOnlyOne(id, value, message);
    }

    @Override
    public String getAdminPassword() {
        return userMapper.getAdminPassword();
    }

    public int getTotalUserCount(){
        return userMapper.getTotalUserCount();
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public int getUserByWhichCount(Select select){
        return userMapper.getUserByWhichCount(select);
    }

    @Override
    public void deleteUserById(Integer id) {

    }


}
