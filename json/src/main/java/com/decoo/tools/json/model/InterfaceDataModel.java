package com.decoo.tools.json.model;

import lombok.Data;

/**
 * @author ghd
 * @date 2019/12/13 14:24
 */
@Data
public class InterfaceDataModel {
    /**
     * 接口key
     */
    private String interfaceKey;

    /**
     * 系统key
     */
    private String systemKey;

    /**
     * 请求体
     */
    private String reqBody;

    /**
     * 字段校验规则
     */
    private String checkRule;

    /**
     * 特殊规则
     */
    private String specialRule;

}
