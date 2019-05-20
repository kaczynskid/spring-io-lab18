package io.spring.lab.marketing.special.calculate;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SpecialCalculation {

	private final String specialId;

	private final BigDecimal totalPrice;
}
