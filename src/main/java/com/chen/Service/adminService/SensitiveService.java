package com.chen.Service.adminService;

import com.chen.mapper.SensitiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensitiveService implements SensitiveMapper {

    @Autowired
    private SensitiveMapper sensitiveMapper;

    @Override
    public List<String> getSensitiveWord() {
        return sensitiveMapper.getSensitiveWord();
    }
}
