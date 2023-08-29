package com.chen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String introduction;
    private int topicCount;
    private int commentCount;
    private int rankId;
    private int supportCount;
    private int rankLevel;
    private int sex;
    private int browseCount;
    private int secret;
    private int ban;
    private int badCount;
    private String secretStr;
}
