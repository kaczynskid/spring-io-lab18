package io.spring.lab.store.basket;

import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;

import io.spring.lab.math.MathProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static io.spring.lab.store.basket.BasketStatus.CLOSED;
import static io.spring.lab.store.basket.BasketStatus.OPEN;
import static java.math.BigDecimal.ZERO;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

	private Long id;

	private BasketStatus status = OPEN;

	private int totalCount = 0;

	private BigDecimal totalPrice = ZERO;

	public Basket(Long id) {
		this.id = id;
	}

	public void update(BasketUpdateDiff diff, MathProperties math) {
		Validate.notNull(diff, "BasketUpdateDiff cannot be null");
		Validate.notNull(math, "Math cannot be null");
		assertNotCheckedOut();

		int newCount = totalCount + diff.getCountDiff();
		Validate.isTrue(newCount >= 0, "Basket total count cannot be negative");

		BigDecimal newPrice = totalPrice.add(diff.getPriceDiff(), math.getContext());
		Validate.isTrue(newPrice.compareTo(ZERO) >= 0, "Basket total price cannot be negative");

		totalCount = newCount;
		totalPrice = newPrice;
	}

	public void checkout() {
		assertNotCheckedOut();

		if (totalCount == 0) {
			throw new EmptyBasket(this);
		}

		status = CLOSED;
	}

	void assertNotCheckedOut() {
		if (CLOSED.equals(status)) {
			throw new AlreadyCheckedOut(this);
		}
	}
}
