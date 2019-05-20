package io.spring.lab.store.special;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SpecialCalculation {

	private final String specialId;

	private final BigDecimal totalPrice;
}
