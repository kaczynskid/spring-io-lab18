package io.spring.lab.store.basket.item;

import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;

import io.spring.lab.math.MathProperties;
import io.spring.lab.store.basket.BasketUpdateDiff;
import io.spring.lab.store.item.ItemRepresentation;
import io.spring.lab.store.special.SpecialCalculation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.math.BigDecimal.ZERO;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BasketItem {

	private Long id;

	private Long basketId;

	private Long itemId;

	private String name;

	private BigDecimal unitPrice = ZERO;

	private int unitCount = 0;

	private BigDecimal totalPrice = ZERO;

	private String specialId;

	public BasketItem(Long basketId, Long itemId) {
		this.basketId = basketId;
		this.itemId = itemId;
	}

	BasketUpdateDiff update(ItemRepresentation changes, int newUnitCount, SpecialCalculation special, MathProperties math) {
		Validate.notNull(changes, "Item changes cannot be null");
		Validate.isTrue(newUnitCount > 0, "UnitCount must be positive");
		Validate.notNull(special, "SpecialCalculation cannot be null");
		Validate.notNull(math, "Math cannot be null");

		name = changes.getName();
		unitPrice = changes.getPrice();
		specialId = special.getSpecialId();
		return new BasketUpdateDiff(
				updateItemCount(newUnitCount),
				updateTotalPrice(special.getTotalPrice(), math));
	}


	private int updateItemCount(int newUnitCount) {
		int diff = newUnitCount - unitCount;
		unitCount = newUnitCount;
		return diff;
	}

	private BigDecimal updateTotalPrice(BigDecimal newTotalPrice, MathProperties math) {
		BigDecimal diff = newTotalPrice.subtract(totalPrice, math.getContext());
		totalPrice = newTotalPrice;
		return diff;
	}
}
