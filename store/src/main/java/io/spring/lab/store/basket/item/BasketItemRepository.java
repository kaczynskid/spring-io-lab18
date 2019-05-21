package io.spring.lab.store.basket.item;

import java.util.List;
import java.util.Optional;

public interface BasketItemRepository {

	BasketItem save(BasketItem basketItem);

	BasketItem saveAndFlush(BasketItem basketItem);

	void delete(BasketItem basketItem);

	List<BasketItem> findByBasketId(long basketId);

	Optional<BasketItem> findByBasketIdAndItemId(long basketId, long itemId);
}
