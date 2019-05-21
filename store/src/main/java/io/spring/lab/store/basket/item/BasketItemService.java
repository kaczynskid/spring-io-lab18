package io.spring.lab.store.basket.item;

import java.util.List;

import io.spring.lab.math.MathProperties;
import io.spring.lab.store.basket.BasketUpdateDiff;
import io.spring.lab.store.item.ItemRepresentation;
import io.spring.lab.store.item.ItemStockUpdate;
import io.spring.lab.store.item.ItemsClient;
import io.spring.lab.store.special.SpecialCalculation;
import io.spring.lab.store.special.SpecialClient;
import lombok.AllArgsConstructor;

import static io.spring.lab.store.special.SpecialCalculationRequest.requestCalculationFor;

@AllArgsConstructor
public class BasketItemService {

	private final BasketItemRepository basketItems;
	private final ItemsClient items;
	private final SpecialClient specials;
	private final MathProperties math;

	public List<BasketItem> findAllItems(long basketId) {
		return basketItems.findByBasketId(basketId);
	}

	public BasketItem findOneItem(long basketId, long itemId) {
		return basketItems.findByBasketIdAndItemId(basketId, itemId).orElse(null);
	}

	public BasketUpdateDiff updateItem(long basketId, long itemId, int count) {
		BasketItem basketItem = basketItems.findByBasketIdAndItemId(basketId, itemId)
				.orElse(newBasketItem(basketId, itemId));

		int newUnitCount = basketItem.getUnitCount() + count;
		if (newUnitCount > 0) {
			return updateInBasket(basketItem, newUnitCount);
		} else {
			return removeFromBasket(basketItem);
		}
	}

	private static BasketItem newBasketItem(long basketId, long itemId) {
		return new BasketItem(basketId, itemId);
	}

	private BasketUpdateDiff updateInBasket(BasketItem basketItem, int count) {
		ItemRepresentation changes = items.findOne(basketItem.getItemId());

		SpecialCalculation calculation = specials.calculateFor(
				basketItem.getItemId(),
				requestCalculationFor(changes.getPrice(), count));

		BasketUpdateDiff diff = basketItem.update(changes, count, calculation, math);
		basketItems.save(basketItem);
		return diff;
	}

	private BasketUpdateDiff removeFromBasket(BasketItem basketItem) {
		BasketUpdateDiff diff = BasketUpdateDiff.ofItem(basketItem);
		basketItems.delete(basketItem);
		return diff;
	}

	public void checkout(long basketId) {
		basketItems.findByBasketId(basketId).forEach(basketItem ->
				items.updateStock(new ItemStockUpdate(basketItem.getItemId(), -basketItem.getUnitCount()))
		);
	}
}
