package com.decoo.tools.json.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author ghd
 * @date 2019/12/13 15:04
 */
public class JsonUtil {

    /**
     * 判断字符串是否是json串
     *
     * @param string 字符串
     * @return boolean
     */
    public static boolean isJson(String string) {
        try {
            JSONObject.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
