package com.chen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private int id;
    private int toId;
    private int fromId;
    private int subject;
    private int status;
    private String title;
    private String content;
    private Date time;
    private int topicId;
    private int commentId;

    private User fromUser;
}
