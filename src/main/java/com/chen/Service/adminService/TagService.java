package com.chen.Service.adminService;

import com.chen.mapper.TagMapper;
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

}
