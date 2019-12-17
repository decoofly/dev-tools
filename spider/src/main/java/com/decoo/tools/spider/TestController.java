package com.decoo.tools.spider;

import com.decoo.tools.spider.service.HttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author ghd
 * @date 2019/12/17 15:15
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    private final HttpService httpService;

    @PostMapping("test")
    public void test() {
        try {
            httpService.getRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
