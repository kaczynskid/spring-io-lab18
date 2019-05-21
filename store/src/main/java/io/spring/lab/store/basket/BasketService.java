package io.spring.lab.store.basket;

import java.util.function.Supplier;

import io.spring.lab.math.MathProperties;
import io.spring.lab.store.basket.item.BasketItemService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BasketService {

	private final BasketRepository baskets;
	private final BasketItemService items;
	private final MathProperties math;

	public Basket create() {
		return baskets.save(new Basket());
	}

	public Basket findOne(long id) {
		return baskets.findOne(id)
				.orElseThrow(() -> new NullPointerException("Basket " + id + " not found!"));
	}

	public BasketUpdateDiff updateItem(long basketId, long itemId, int count) {
		return updateBasket(basketId, () -> items.updateItem(basketId, itemId, count));
	}

	private BasketUpdateDiff updateBasket(long basketId, Supplier<BasketUpdateDiff> diffSupplier) {
		Basket basket = findOne(basketId);
		basket.assertNotCheckedOut();

		BasketUpdateDiff diff = diffSupplier.get();

		basket.update(diff, math);
		baskets.save(basket);

		return diff;
	}

	public Basket checkout(long id) {
		Basket basket = findOne(id);
		basket.assertNotCheckedOut();

		basket.checkout();

		items.checkout(basket.getId());

		baskets.save(basket);

		return basket;
	}
}
