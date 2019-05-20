package io.spring.lab.math;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(MathProperties.class)
@EnableConfigurationProperties(MathProperties.class)
public class MathAutoConfiguration {

}
