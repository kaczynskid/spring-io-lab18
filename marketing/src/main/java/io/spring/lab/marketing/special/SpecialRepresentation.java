package io.spring.lab.marketing.special;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialRepresentation {

	private long itemId;

	private int batchSize;

	private BigDecimal batchPrice;

	public static SpecialRepresentation of(Special special) {
		return new SpecialRepresentation(special.getItemId(), special.getBatchSize(), special.getBatchPrice());
	}
}
