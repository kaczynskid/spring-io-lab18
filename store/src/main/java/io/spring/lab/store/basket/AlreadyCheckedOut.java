package io.spring.lab.store.basket;

class AlreadyCheckedOut extends RuntimeException {

	AlreadyCheckedOut(Basket basket) {
		super(String.format("Basket %d is already checked out", basket.getId()));
	}
}

