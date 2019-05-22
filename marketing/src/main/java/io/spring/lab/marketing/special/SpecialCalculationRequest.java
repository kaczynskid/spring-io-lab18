package io.spring.lab.marketing.special;

import java.math.BigDecimal;

import lombok.Data;

@Data
class SpecialCalculationRequest {

	private BigDecimal unitPrice;
	private int unitCount;
}
