package com.chen.Service.adminService;

import com.chen.mapper.IpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpService implements IpMapper {

    @Autowired
    private IpMapper ipMapper;

    @Override
    public void addIp(String ip) {

        List allIp = ipMapper.getAllIp();

        for (Object i : allIp) {
            if (!i.equals(ip)){
                continue;
            }else {
                return;
            }
        }
        ipMapper.addIp(ip);
    }

    public int getIpCount(){
        return ipMapper.getAllIp().size();
    }

    @Override
    public List<String> getAllIp() {
        return null;
    }
}
