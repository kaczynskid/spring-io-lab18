package io.spring.lab.store;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(considerNestedRepositories = true)
public class StorePersistenceConfig {
}
