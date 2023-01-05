package com.chen.mapper;

import com.chen.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Property;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {

    List<Tag> getTagList();

    String getTagNameById(Integer id);

    List<Tag> getTagListByFuzzyName(String message);
}
