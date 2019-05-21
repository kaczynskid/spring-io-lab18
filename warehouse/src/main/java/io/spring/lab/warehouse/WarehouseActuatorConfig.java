package io.spring.lab.warehouse;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemRepository;

import static java.util.Collections.singletonMap;

@Configuration
public class WarehouseActuatorConfig {

    @Bean
    HealthIndicator countHealthIndicator(ItemRepository items) {
        return () -> {
            long count = items.count();
            Status status = count % 2 == 0 ? Status.UP : Status.DOWN;
            return Health.status(status)
                    .withDetail("sum", count)
                    .build();
        };
    }

    @Bean
    InfoContributor stockInfoContributor(ItemRepository items) {
        return builder -> {
            builder.withDetail("items", singletonMap("stock",
                    items.findAll().stream().mapToInt(Item::getCount).sum()));
        };
    }
}
