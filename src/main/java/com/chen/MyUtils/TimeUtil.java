package com.chen.MyUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeUtil {

    /** 获取当前系统时间*/
    private long startTime;

    /** 获取当前的系统时间，与初始时间相减就是程序运行的毫秒数，除以1000就是秒数*/
    private long endTime;

    public void start(){
        setStartTime(System.currentTimeMillis());
    }

    public void end(){
        setEndTime(System.currentTimeMillis());
    }

    public long getCodeTime() {
        return (System.currentTimeMillis()-startTime)/1000;
    }

    public long setTime(){
        return System.currentTimeMillis();
    }

    public long getTime(long time){
        return (System.currentTimeMillis()-time)/1000;
    }
}
