package com.decoo.tools.json.controller;

import com.decoo.tools.json.model.InterfaceDataModel;
import com.decoo.tools.json.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ghd
 * @date 2019/12/13 14:14
 */
@RestController
@Slf4j
public class JsonCheckController {


    @PostMapping("/check")
    public void ckeckResult(@RequestBody InterfaceDataModel dataModel) {
        if (!JsonUtil.isJson(dataModel.getReqBody())) {
            log.info("接口数据不是json格式，请检查！");
        }
        //校验规则获取
        String fieldCheckRule = dataModel.getCheckRule();

        //接口key
        String interfaceKey = dataModel.getInterfaceKey();

        //系统key
        String systemKey = dataModel.getSystemKey();



    }
}
