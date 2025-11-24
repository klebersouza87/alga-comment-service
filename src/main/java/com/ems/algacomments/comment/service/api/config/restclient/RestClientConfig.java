package com.ems.algacomments.comment.service.api.config.restclient;

import com.ems.algacomments.comment.service.api.client.CommentModerationClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public CommentModerationClient commentModerationClient(RestClientFactory factory) {
        RestClient restClient = factory.moderationRestClient();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

        return proxyFactory.createClient(CommentModerationClient.class);
    }

}
