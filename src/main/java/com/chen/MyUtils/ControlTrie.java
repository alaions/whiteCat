package com.chen.MyUtils;

import com.chen.config.Trie;
import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class ControlTrie {

    private Trie topicTrie;

    private Trie userTrie;

    private Trie sensitiveTrie;

    public ControlTrie(){
        topicTrie = new Trie();
        userTrie = new Trie();
        sensitiveTrie = new Trie();
    }

}
