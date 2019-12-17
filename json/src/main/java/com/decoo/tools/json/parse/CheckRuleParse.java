package com.decoo.tools.json.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.decoo.tools.json.model.FieldModel;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ghd
 * @date 2019/12/13 15:12
 */
@Component
public class CheckRuleParse {

    /**
     * @param systemKey    系统key
     * @param interfaceKey 接口key
     * @param rules         校验规则
     */
    public void parseInterfaceCheckRule(String systemKey, String interfaceKey, String rules) {
        /**
         * {field1: $.path,feild2: $.rule}
         */

        String data = "";
        Map<String,Object> dataMap = parseSaveRule(data,rules);

        for(String key: dataMap.keySet()) {
            handleFieldCheck(key,dataMap.get(key));
        }
    }

    private void handleFieldCheck(String key, Object o) {
        //查询数据库得到key对应的字段
        FieldModel model = new FieldModel();
        if (key.equals(model.getFieldChar())) {
            String checkRule = model.getCheckRule();
        }

    }

    /**
     * 保存规则解析
     *
     * @param saveRules 保存规则
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseSaveRule(String reqJson, String saveRules) {
        //转换规则json为Map
        JSONObject saveObject = JSONObject.parseObject(saveRules);
        Map<String, Object> map = JSON.toJavaObject(saveObject, Map.class);
        for (String key : map.keySet()) {
            //将请求内容中的数据按规则json中的路径取值并赋值给对应的key
            map.put(key, JSONPath.read(reqJson, map.get(key).toString()));
        }
        return map;

    }
}

