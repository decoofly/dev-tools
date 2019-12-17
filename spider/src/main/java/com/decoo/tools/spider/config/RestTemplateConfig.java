package com.decoo.tools.spider.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author decoo
 * @date 2019/12/17 15:04
 */
@Configuration
public class RestTemplateConfig {

    /**
     * restTemplate
     * @param builder build
     * @return RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
