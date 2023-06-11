package com.chen.controller.UserController;

import com.chen.MyUtils.ControlTrie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TrieSearchController {

    @Autowired
    ControlTrie controlTrie;

    @GetMapping("/searchTopic/{msg}")
    @ResponseBody
    public List<String> searchTopic(@PathVariable("msg") String q){
        List<String> list = controlTrie.getTopicTrie().searchListPlus(q);
        return list;
    }

    @GetMapping("/searchUser/{msg}")
    @ResponseBody
    public List<String> searchUser(@PathVariable("msg") String q){
        List<String> list = controlTrie.getUserTrie().searchListPlus(q);
        return list;
    }
}
