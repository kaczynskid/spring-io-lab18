package io.spring.lab.marketing;

import java.math.BigDecimal;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.spring.lab.marketing.special.Special;
import io.spring.lab.marketing.special.SpecialRepository;

@SpringBootApplication
public class MarketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketingApplication.class, args);
    }

    @Bean
    public static ApplicationRunner testDataInitializer(SpecialRepository specials) {
        return args -> {
            specials.save(new Special(null, 1, 3, BigDecimal.valueOf(70)));
            specials.save(new Special(null, 2, 2, BigDecimal.valueOf(15)));
            specials.save(new Special(null, 3, 4, BigDecimal.valueOf(60)));
            specials.save(new Special(null, 4, 2, BigDecimal.valueOf(40)));
        };
    }
}
