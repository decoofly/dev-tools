package com.decoo.tools.spider.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingDeque;

/**
 * @author ghd
 * @date 2019/12/17 13:40
 */
@Service
public class SpiderService {


    public void process() {
        //过滤url
        filterUrl();

        //单个url请求
        requestUrl();

    }

    private void requestUrl() {
        BlockingDeque<List<String>> d;
    }

    private void filterUrl() {

    }
}
