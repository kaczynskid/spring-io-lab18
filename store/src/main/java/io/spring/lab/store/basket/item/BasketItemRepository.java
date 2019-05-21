package io.spring.lab.store.basket.item;

import java.util.List;
import java.util.Optional;

public interface BasketItemRepository {

	boolean isEmpty();

	long count();

	BasketItem save(BasketItem basketItem);

	BasketItem saveAndFlush(BasketItem basketItem);

	void delete(BasketItem basketItem);

	List<BasketItem> findByBasketId(long basketId);

	Optional<BasketItem> findByBasketIdAndItemId(long basketId, long itemId);
}
