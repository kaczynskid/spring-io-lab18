package io.spring.lab.store.item;

public interface ItemsClient {

    ItemRepresentation findOne(long id);

    void updateStock(ItemStockUpdate changes);
}
