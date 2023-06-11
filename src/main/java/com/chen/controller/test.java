package com.chen.controller;

import com.chen.MyUtils.ControlTrie;
import com.chen.config.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class test {

    @Autowired
    ControlTrie controlTrie;

    @GetMapping("/commons")
    public String test(){
        return "commons/commons";
    }

    @GetMapping("trie")
    public String trie(){
        return "trie";
    }


}
