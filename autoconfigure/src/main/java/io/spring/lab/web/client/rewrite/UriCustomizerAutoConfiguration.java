package io.spring.lab.web.client.rewrite;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static io.spring.lab.web.client.rewrite.UriCustomizer.compose;
import static java.util.Collections.addAll;

@Configuration
@ConditionalOnClass({ RestTemplate.class, RestTemplateCustomizer.class })
@ConditionalOnUriRewrite
@EnableConfigurationProperties(UriCustomizerProperties.class)
public class UriCustomizerAutoConfiguration {

    @Bean
    RestTemplateCustomizer uriRewritingCustomizer(List<UriCustomizer> customizers) {
        return restTemplate -> addAll(restTemplate.getInterceptors(), new UriRewritingInterceptor(compose(customizers)));
    }
}
