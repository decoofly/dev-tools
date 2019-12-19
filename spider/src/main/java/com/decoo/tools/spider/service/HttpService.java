package com.decoo.tools.spider.service;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author ghd
 * @date 2019/12/17 13:46
 */
@Service
@RequiredArgsConstructor
public class HttpService {
    private final RestTemplate restTemplate;


    public void getRequest() throws IOException {
        for (int i = 0; i < 30; i++) {
            String address = "https://Movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=" + i;
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet get = new HttpGet(address);
            get.setHeader("user-agent", userAgent);

            CloseableHttpResponse response = httpClient.execute(get);

            Document document = Jsoup.connect(address).userAgent(userAgent).get();
//            ResponseEntity<JSONObject> forEntity = restTemplate.getForEntity(address, JSONObject.class);
        }

    }


}
