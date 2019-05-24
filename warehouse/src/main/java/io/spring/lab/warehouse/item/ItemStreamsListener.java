package io.spring.lab.warehouse.item;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.spring.lab.warehouse.item.ItemStreamsConfig.STOCK_UPDATE;

@Slf4j
@Component
@AllArgsConstructor
public class ItemStreamsListener {

    private final ItemService items;

    @StreamListener(STOCK_UPDATE)
    public void updateStock(ItemStockUpdate changes) {
        log.info("Update item stock event {}.", changes);
        items.updateStock(changes);
    }
}
