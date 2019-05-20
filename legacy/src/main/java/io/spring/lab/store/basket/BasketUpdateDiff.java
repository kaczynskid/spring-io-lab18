package io.spring.lab.store.basket;

import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;

import io.spring.lab.store.basket.item.BasketItem;
import lombok.Value;

@Value
public class BasketUpdateDiff {

	private final int countDiff;

	private final BigDecimal priceDiff;

	public BasketUpdateDiff(int countDiff, BigDecimal priceDiff) {
		Validate.notNull(priceDiff, "PriceDiff cannot be null");
		
		this.countDiff = countDiff;
		this.priceDiff = priceDiff;
	}

	public static BasketUpdateDiff ofItem(BasketItem item) {
		return new BasketUpdateDiff(-item.getUnitCount(), item.getTotalPrice().negate());
	}
}
