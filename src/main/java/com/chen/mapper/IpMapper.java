package com.chen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface IpMapper {
    void addIp(@Param("ip") String ip, @Param("time") Date time);

    List<String> getAllIp();
}
