package com.chen.MyUtils;

import org.springframework.util.StringUtils;

public class MyStringUtil {

    /**
     * 校验字符串是否非空
     *
     * @param str 字符串组合
     * @return 所有字符串都非空时返回 true，否则返回 false
     */
    public static Boolean notEmpty(String... str) {
        for (String strIndex : str) {
            if (StringUtils.isEmpty(strIndex)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验字符串序列是否含有空值
     *
     * @param str 字符串序列
     * @return 只要有一个字符串为空时就返回 true，否则返回 false
     */
    public static Boolean isEmpty(String... str) {
        return !notEmpty(str);
    }

}
