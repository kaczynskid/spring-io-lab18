package io.spring.lab.store.item;

import java.util.List;

public interface ItemsClient {

    List<ItemRepresentation> findAll();

    ItemRepresentation findOne(long id);

    void updateStock(ItemStockUpdate changes);
}
