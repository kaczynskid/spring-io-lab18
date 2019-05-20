package io.spring.lab.warehouse.item;

class OutOfStock extends RuntimeException {

	OutOfStock(Item item, int count) {
		super(String.format("Item %s has only %d out of %d requested", item.getName(), item.getCount(), count));
	}
}
