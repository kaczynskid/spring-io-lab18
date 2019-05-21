package io.spring.lab.marketing.special;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.spring.lab.marketing.special.calculate.SpecialCalculation;
import io.spring.lab.marketing.special.calculate.SpecialCalculator;
import io.spring.lab.math.MathProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.math.BigDecimal.ZERO;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Special implements SpecialCalculator {

	@Id
	private String id;

	@NotNull
	private long itemId;

	@NotNull
	@Min(2)
	private int batchSize = 1;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal batchPrice = ZERO;

	public SpecialCalculation calculateFor(int unitCount, BigDecimal unitPrice, MathProperties math) {
		Validate.isTrue(unitCount > 0, "UnitCount must be positive");
		Validate.notNull(unitPrice, "UnitPrice cannot be null");
		Validate.isTrue(unitPrice.compareTo(ZERO) > 0, "UnitPrice must be positive");
		Validate.notNull(math, "Math cannot be null");

		if (appliesFor(unitCount)) {
			return calculateSpecialPrice(unitCount, unitPrice, math);
		} else {
			return regularPrice.calculateFor(unitCount, unitPrice, math);
		}
	}

	private boolean appliesFor(int unitCount) {
		return unitCount >= batchSize;
	}

	private SpecialCalculation calculateSpecialPrice(int unitCount, BigDecimal unitPrice, MathProperties math) {
		BigDecimal batchesPrice = batchesPriceOf(unitCount);
		BigDecimal reminderPrice = reminderPriceOf(unitCount, unitPrice);
		BigDecimal totalPrice = batchesPrice.add(reminderPrice, math.getContext());
		return new SpecialCalculation(id, totalPrice);
	}

	private BigDecimal batchesPriceOf(int unitCount) {
		int batchesCount = unitCount / batchSize;
		return batchPrice.multiply(BigDecimal.valueOf(batchesCount));
	}

	private BigDecimal reminderPriceOf(int unitCount, BigDecimal unitPrice) {
		int reminderCount = unitCount % batchSize;
		return unitPrice.multiply(BigDecimal.valueOf(reminderCount));
	}
}
