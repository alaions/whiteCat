package com.chen.config;
import com.chen.MyUtils.PinYinUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component
public class Trie implements Serializable {

    private List<String> list;
    private Trie trie;
    private TrieNode root ; // 根节点
    private static final int SUM = 10;

    static class TrieNode {

        // 标识是否为终节点
        Boolean isEnd;
        // 多少个路线通过该节点
        Integer num;
        // 保存所有儿子节点,从root开始每个非end节点都有很多分支，分支用Map来表示
        Map<String,TrieNode> sonMap;
        // 标记是否存在儿子节点
        Boolean hasSon;
        // 存储字符拼音首拼
        String pinYin = null;

        TrieNode() {
            isEnd = false;
            hasSon = false;
            num = 1;
            sonMap = new HashMap<String, TrieNode>();
        }
    }

    public Trie() {
        root = new TrieNode();
    }

    public Trie getTrie() {
        if (trie == null) {
            trie = new Trie();
        }
        return trie;
    }

    public List<String> getList() {
        return list;
    }

    public void setTrie(Trie trie) {
        this.trie = trie;
    }

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    /**
     * 当节点增加时，resultList和字典树更新
     * @param word
     */
    public void renew(String word){
        list.add(word);
        this.insertPlus(word);
    }

    /**
     * 插入字典树 支持拼音与英文与中文
     * @param sentence
     */
    public void insertPlus(String sentence) {
        TrieNode node = root;
        for (int i=0; i<sentence.length(); i++) {
            String word = String.valueOf(sentence.charAt(i));
            // 如果树中不存在该字
            if (!node.sonMap.containsKey(word)) {
                node.sonMap.put(word,new TrieNode());
                node.hasSon = true;
                //不忽略英文，将首字母放入
                node.sonMap.get(word).pinYin = PinYinUtils.getFirstSpell(word);
            } else {
                node.num ++;
            }
            node = node.sonMap.get(word); // 指针下移
        }
        node.isEnd = true;
    }

    /**
     * 插入字典树 仅支持英文与中文
     * @param sentence
     */
    public void insert(String sentence) {

        TrieNode node = root;

        for (int i=0; i<sentence.length(); i++) {
            String word = String.valueOf(sentence.charAt(i));
            // 如果树中不存在该字
            if (!node.sonMap.containsKey(word)) {
                node.sonMap.put(word,new TrieNode());
                node.hasSon = true;
            } else {
                node.num ++;
            }
            node = node.sonMap.get(word); // 指针下移
        }

        node.isEnd = true; // 终点

    }

    /**
     * 在树中查找
     * @param sentence
     * @return
     */
    public boolean search(String sentence) {

        TrieNode node = root;

        for (int i=0; i<sentence.length(); i++) {
            String word = String.valueOf(sentence.charAt(i));
            if (node.sonMap.containsKey(word)) {

                node = node.sonMap.get(word);

            } else {
                return false;
            }
        }

        return node.isEnd;
    }

    /**
     * 查找一个句子中是否包含树中的词，敏感词过滤
     * @param sentence
     * @return
     */
    public boolean searchWord(String sentence, TrieNode node, int index) {
        for (int i = index; i < sentence.length(); i++){
            String word = String.valueOf(sentence.charAt(i));
            if (node.sonMap.containsKey(word)){
                node = node.sonMap.get(word);
                if (node.isEnd){
                    return true;
                }
            } else {
                // 搜索不到则从sentence的下一个字符开始从新搜索
                return searchWord(sentence, root, index + 1);
            }
        }
        return false;
    }

    public boolean searchWord(String sentence){
        return searchWord(sentence, root, 0);
    }

    /**
     * 获取所有以入参关键字开头的词汇
     * @param sentence
     * @return
     */
    public List<String> searchList(String sentence) {

        List<String> resultList = new ArrayList<>();
        TrieNode node = root;

        // 解析sentence
        for (int i=0; i<sentence.length(); i++) {

            String word = String.valueOf(sentence.charAt(i));
            // 如果树中不包含关键字，直接返回, 每次搜索输入的字符都要遍历到最后一个字符
            // 如果在遍历的途中有一个字符没被包含则返回空的resultList
            if (node.sonMap.containsKey(word)) {
                node = node.sonMap.get(word);
            } else {
                return resultList;
            }
            // 判断如果是一句话中的最后一个字符且有子节点
            if (i == sentence.length() -1 && node.hasSon) {
                // recursion中字符串put进resultList
                resultList = dfsRecursion(node, resultList, sentence);
            }
        }
        return resultList;
    }

    /**
     * 检索增强版，支持中文/拼音首拼/英文混合搜索
     * @param nowNode
     * @param sentence
     * @param index
     * @param resultListCh
     * @param prefix
     * @return
     */
    public List<String> searchListPlus(TrieNode nowNode, String sentence, int index, List<String> resultListCh, StringBuffer prefix) {
        //判断是否为递归出口
        //当index == sentence前缀的最后1个字符 + 1时递归结素
        if (index == sentence.length()){
            if (nowNode.isEnd){
                //到达出口后，若该前缀也为结果，将前缀对应的结果加入
                resultListCh.add(prefix.toString());
            }
            // 出口处将节点给dfsRecursion()去搜索所有子节点
            return dfsRecursion(nowNode, resultListCh, prefix.toString());
        }
        // 解析sentence，word为前缀中的一个字符
        String word = String.valueOf(sentence.charAt(index));
        // 判断当前字符word是否为中文
        if (PinYinUtils.isChineseChar(word.charAt(0))){
            //如果word为中文
            if (nowNode.sonMap.containsKey(word)){
                // 就将该中文加到prefix中
                prefix.append(word);
                // 继续递归搜索前缀
                resultListCh = searchListPlus(nowNode.sonMap.get(word), sentence, index + 1, resultListCh, prefix);
            }

        } else { //如果word是字母
            // 下一个节点
            TrieNode nextNode;
            // 遍历当前节点的sonMap，得到nextNode
            for(String key : nowNode.sonMap.keySet()){

                nextNode = nowNode.sonMap.get(key);
                //判断nextNode中的pinYin与word(前缀中的第 index-1 个字符)是否相等
                if (Objects.equals(word, nextNode.pinYin)){
                    // 若相等这将该字符加入prefix
                    prefix.append(key);
                    // 继续递归搜索前缀 index + 1
                    resultListCh = searchListPlus(nextNode, sentence, index + 1, resultListCh, prefix);
                    // 将刚刚加入prefix的字符删去
                    prefix.deleteCharAt(prefix.length()-1);
                }
            }
        }

        return resultListCh;
    }


    /**
     * searchListPlus重载，方便调用
     * @param sentence
     * @return
     */
    public List<String> searchListPlus(String sentence){
        return searchListPlus(root, sentence, 0, new ArrayList<>(), new StringBuffer());
    }

    /**
     * 递归获取字典树的树枝。
     * @param node
     * @return
     */
    private List<String> dfsRecursion(TrieNode node, List<String> resultList, String sentence) {
        //控制查询的个数
        if (resultList.size() > SUM){
            return resultList;
        }
        if (node.hasSon) {
            //遍历当层的节点是否为end节点
            for (String key : node.sonMap.keySet()) {
                if (node.sonMap.get(key).isEnd) {
                    resultList.add(sentence + key); // 当前节点是end节点，则add 前缀+key == 完整单词（每个key都是单个字符）
                }
                // 递归判断 下一层 节点是否为end节点，前缀更新为sb + key
                resultList = dfsRecursion(node.sonMap.get(key), resultList, sentence + key);
            }
        }
        return resultList;
    }

    public static void print(TrieNode root){
        TrieNode nextnode = null;
        for (String key : root.sonMap.keySet()) {
            System.out.print(key);
            nextnode = root.sonMap.get(key);
            print(nextnode);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Trie chTrie = new Trie();
       // String str = "word文档,wifi万能钥匙,windows10,网易云游戏,word的删除空白页,微信,万相之王,微博,万能钥匙wifi免费下载,维生素c的作用与功效";
        String str = "扎汗俄马尔,飞机场,飞机翼,fjall,fjeld,福建政府,福建船政，";
        String[] splits = str.split(",");
        System.out.println();
        // 循环插入字典树中
        for (String split : splits) {
            chTrie.insertPlus(split);
        }
        String strs = "fjal";
        System.out.println(chTrie.search(strs));
        System.out.println(chTrie.searchList(strs));
        System.out.println(chTrie.searchListPlus(strs));
        //System.out.println(chTrie.searchListPlus(chTrie.root, strs, 0, new ArrayList<>(), new StringBuffer()));

    }
}
