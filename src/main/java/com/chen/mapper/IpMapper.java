package com.chen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IpMapper {
    void addIp(String ip);

    List<String> getAllIp();
}
