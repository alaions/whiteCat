package com.chen.Service.adminService;

import com.chen.mapper.TagMapper;
import com.chen.pojo.Select;
import com.chen.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements TagMapper{

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTagList() {
        return tagMapper.getTagList();
    }


    public String getTagNameById(Integer id){
        return tagMapper.getTagNameById(id);
    }

    @Override
    public List<Tag> getTagListByFuzzyName(String message) {
        return tagMapper.getTagListByFuzzyName(message);
    }

    @Override
    public Integer getTotalTagCount() {
        return tagMapper.getTotalTagCount();
    }

    @Override
    public Tag getTagById(Integer id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public void insertTag(String name) {
        tagMapper.insertTag(name);
    }

    @Override
    public void deleteById(Integer id) {
        tagMapper.deleteById(id);
    }

    @Override
    public void summitUpdate(Integer id, String newName) {
        tagMapper.summitUpdate(id, newName);
    }


}
