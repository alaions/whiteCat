package com.chen.mapper;

import com.chen.pojo.Select;
import com.chen.pojo.User;
import jdk.nashorn.internal.objects.annotations.Property;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.image.IntegerInterleavedRaster;

import java.util.List;
import java.util.Map;

//记得@mapper
@Repository
@Mapper
public interface UserMapper {

    @org.apache.ibatis.annotations.Select({
            "select * from user where username = #{username}"
    })
    User get(String username);

    @org.apache.ibatis.annotations.Select({
            "select username from user"
    })
    List<String> getTotalName();

    String getAdminPassword();

    int getTotalUserCount();

    User getUserByUsername(String username);

    List<User> getUserList();

    User getUserById(@Param("id") Integer id);

    List<User> getUserByWhich(Select select);

    int getUserByWhichCount(Select select);

    void deleteUserById(@Param("id") Integer id);

    void updateUserBadCount(User user);

    void banUser(User user);

    void NonBanUser(User user);

    void updateUserById(User user);

    void insertUser(User user);

    void topicCountPlus(Integer id);

    void commentCountPlus(Integer id);

    void browseCountPlus(Integer id);

    void supportCountPlus(Integer id);

    void supportCountMinus(Integer id);

    void topicCountMinus(Integer id);

    List<User> getHotCommentUser(int count);

    void updateUserLimit(User user);

    User getUserByEmail(String email);

    List<User> getUserListByMessage(String namMessage);

    void updateOnlyOne(@Param("id") Integer id, @Param("value") String value, @Param("message") String message);


}
