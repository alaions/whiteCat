package com.chen.mapper;

import com.chen.pojo.Comment;
import com.chen.pojo.Select;
import com.chen.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    List<Comment> getCommentListByTopicId(@Param("topicId") Integer topicId, @Param("floor") Integer floor);

    List<Comment> getCommentList();

    Integer getCommentByWhichCount(Select select);

    List<Comment> getCommentByWhich(Select select);

    Integer getTotalCommentCount();

    User getUserById(Integer id);

    int getMaxParentCommentFloor(int commentTopicId);

    int getMaxChildCommentFloor(@Param("commentTopicId") int commentTopicId, @Param("parentFloor") int parentFloor);

    void insertComment(Comment comment);

    void updateSupportCount(@Param("commentId") Integer commentId, @Param("count") Integer count);

    Comment getCommentById(Integer id);

    Integer getUserIdByCommentId(@Param("commentId") Integer commentId);

    void deleteComment(@Param("id") Integer id);
}
