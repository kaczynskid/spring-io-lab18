package io.spring.lab.warehouse;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemRepository;

@Configuration
@EnableJpaRepositories(considerNestedRepositories = true)
public class WarehousePersistenceConfig {

    @Bean
    public static ApplicationRunner testDataInitializer(ItemRepository items) {
        return args -> {
            testItemsData().forEach(items::save);
        };
    }

    public static List<Item> testItemsData() {
        return Arrays.asList(
                new Item(null, "A", 100, BigDecimal.valueOf(40.0)),
                new Item(null, "B", 100, BigDecimal.valueOf(10.0)),
                new Item(null, "C", 100, BigDecimal.valueOf(30.0)),
                new Item(null, "D", 100, BigDecimal.valueOf(25.0))
        );
    }
}
