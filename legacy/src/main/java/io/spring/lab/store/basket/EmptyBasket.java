package io.spring.lab.store.basket;

class EmptyBasket extends RuntimeException {

	EmptyBasket(Basket basket) {
		super(String.format("Basket %d is empty", basket.getId()));
	}
}
