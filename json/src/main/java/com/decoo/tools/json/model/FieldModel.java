package com.decoo.tools.json.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ghd
 * @date 2019/12/13 14:38
 */
@Data
@Accessors(chain = true)
public class FieldModel {
    /**
     * 字段对应的字符串
     */
    private String fieldChar;

    /**
     * 字段校验规则
     */
    private String checkRule;

    /**
     * 字段值的获取路径
     */
    private String fieldPath;

    /**
     * 字段校验结果
     */
    private String checkResult;

}
