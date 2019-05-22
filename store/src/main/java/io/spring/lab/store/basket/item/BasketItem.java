package io.spring.lab.store.basket.item;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BasketItem {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotNull
	private Long basketId;

	@NotNull
	private Long itemId;

	@NotNull
	@Column(columnDefinition = "text")
	private String name;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal unitPrice = ZERO;

	@NotNull
	@Min(1)
	private int unitCount = 0;

	@NotNull
	@DecimalMin("0.01")
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
