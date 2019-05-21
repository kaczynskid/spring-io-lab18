package io.spring.lab.store.basket;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;

import io.spring.lab.math.MathProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static io.spring.lab.store.basket.BasketStatus.CLOSED;
import static io.spring.lab.store.basket.BasketStatus.OPEN;
import static java.math.BigDecimal.ZERO;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(STRING)
	private BasketStatus status = OPEN;

	@NotNull
	@Min(0)
	private int totalCount = 0;

	@NotNull
	@Min(0)
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
