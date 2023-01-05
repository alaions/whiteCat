package com.chen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appraise {
    private int id;
    private int userId;
    private int topicId;
    private int type;
    private int commentId;
    private int status;
}
