package io.spring.lab.warehouse.item;

class ItemNotFound extends RuntimeException {

    ItemNotFound(long itemId) {
        super(String.format("Item %s does not exist", itemId));
    }
}
