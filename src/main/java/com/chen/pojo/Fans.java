package com.chen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fans {
    private int id;
    private int userId;
    private int fansId;
    private int status;
    private Date creatTime;
    private Date updateTime;
}
