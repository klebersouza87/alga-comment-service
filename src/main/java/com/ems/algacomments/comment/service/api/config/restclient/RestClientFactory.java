package com.ems.algacomments.comment.service.api.config.restclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class RestClientFactory {

    private final String baseUrl;
    private final RestClient.Builder builder;

    public RestClientFactory(RestClient.Builder builder, @Value("${moderation-service.base-url}") String baseUrl) {
        this.builder = builder;
        this.baseUrl = baseUrl;
    }

    public RestClient moderationRestClient() {
        return builder
                .baseUrl(baseUrl)
                .requestFactory(generateClientHttpRequestFactory())
                .build();
    }

    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(3));
        return factory;
    }

}
