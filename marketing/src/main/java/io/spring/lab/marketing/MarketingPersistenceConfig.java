package io.spring.lab.marketing;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.spring.lab.marketing.special.Special;
import io.spring.lab.marketing.special.SpecialRepository;

@Configuration
@EnableMongoRepositories(considerNestedRepositories = true)
public class MarketingPersistenceConfig {

    @Bean
    public static ApplicationRunner testDataInitializer(SpecialRepository specials) {
        return args -> {
            if (specials.isEmpty()) {
                testSpecialsData().forEach(specials::save);
            }
        };
    }

    public static List<Special> testSpecialsData() {
        return Arrays.asList(
                new Special(null, 1, 3, BigDecimal.valueOf(70.0)),
                new Special(null, 2, 2, BigDecimal.valueOf(15.0)),
                new Special(null, 3, 4, BigDecimal.valueOf(60.0)),
                new Special(null, 4, 2, BigDecimal.valueOf(40.0))
        );
    }
}
