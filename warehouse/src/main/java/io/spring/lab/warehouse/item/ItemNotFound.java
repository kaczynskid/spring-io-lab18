package io.spring.lab.warehouse.item;

public class ItemNotFound extends RuntimeException {

    public ItemNotFound(long itemId) {
        super(String.format("Item %s does not exist", itemId));
    }
}
