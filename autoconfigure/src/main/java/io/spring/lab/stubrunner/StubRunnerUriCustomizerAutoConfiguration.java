package io.spring.lab.stubrunner;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.spring.lab.web.client.rewrite.ConditionalOnUriRewrite;
import io.spring.lab.web.client.rewrite.UriCustomizerAutoConfiguration;

@Configuration
@ConditionalOnUriRewrite
@AutoConfigureBefore(UriCustomizerAutoConfiguration.class)
public class StubRunnerUriCustomizerAutoConfiguration {

    @Bean
    StubRunnerUriCustomizer stubRunnerUriCustomizer(StubFinder stubFinder) {
        return new StubRunnerUriCustomizer(stubFinder);
    }
}
