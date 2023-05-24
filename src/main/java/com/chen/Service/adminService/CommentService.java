package com.chen.Service.adminService;

import com.chen.mapper.CommentMapper;
import com.chen.pojo.Comment;
import com.chen.pojo.Select;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements CommentMapper {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentListByTopicId(Integer topicId, Integer floor) {
        return commentMapper.getCommentListByTopicId(topicId, floor);
    }

    @Override
    public List<Comment> getCommentList() {
        return commentMapper.getCommentList();
    }

    @Override
    public List<Comment> getAllCommentByTopicId(Integer id) {
        return commentMapper.getAllCommentByTopicId(id);
    }

    @Override
    public Integer getCommentByWhichCount(Select select) {
        return commentMapper.getCommentByWhichCount(select);
    }

    @Override
    public List<Comment> getCommentByWhich(Select select) {
        return commentMapper.getCommentByWhich(select);
    }

    @Override
    public Integer getTotalCommentCount() {
        return commentMapper.getTotalCommentCount();
    }

    @Override
    public User getUserById(Integer id) {
        return commentMapper.getUserById(id);
    }

    @Override
    public int getMaxParentCommentFloor(int commentTopicId) {
        return commentMapper.getMaxParentCommentFloor(commentTopicId);
    }

    @Override
    public int getMaxChildCommentFloor(int commentTopicId, int parentFloor) {
        return commentMapper.getMaxChildCommentFloor(commentTopicId, parentFloor);
    }


    @Override
    public void insertComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public void updateSupportCount(Integer commentId, Integer count) {
        commentMapper.updateSupportCount(commentId, count);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentMapper.getCommentById(id);
    }


    @Override
    public Integer getUserIdByCommentId(Integer commentId) {
        return commentMapper.getUserIdByCommentId(commentId);
    }

    @Override
    public void deleteComment(Integer id) {
        commentMapper.deleteComment(id);
    }

    @Override
    public int IfMainComment(Integer parentId) {
        return commentMapper.IfMainComment(parentId);
    }

    @Override
    public int getFloor(Integer commentId) {
        return commentMapper.getFloor(commentId);
    }

    @Override
    public void shield(String shieldWord, Integer commentId) {
        commentMapper.shield(shieldWord, commentId);
    }

    @Override
    public int ifExitsChild(Integer topicId, Integer commentId, Integer parentFloor) {
        return commentMapper.ifExitsChild(topicId, commentId, parentFloor);
    }

    @Override
    public int getTopicId(Integer commentId) {
        return commentMapper.getTopicId(commentId);
    }

    public void deleteCommentListByTopicId(Integer topicId){

        List<Comment> list = commentMapper.getAllCommentByTopicId(topicId);

        for (Comment comment : list) {
            commentMapper.deleteComment(comment.getId());
        }

    }

}
