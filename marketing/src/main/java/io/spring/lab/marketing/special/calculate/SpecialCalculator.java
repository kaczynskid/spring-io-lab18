package io.spring.lab.marketing.special.calculate;

import java.math.BigDecimal;

import io.spring.lab.math.MathProperties;

@FunctionalInterface
public interface SpecialCalculator {

	SpecialCalculator regularPrice = (unitCount, unitPrice, math) ->
			new SpecialCalculation(null, unitPrice.multiply(BigDecimal.valueOf(unitCount)));

	SpecialCalculation calculateFor(int unitCount, BigDecimal unitPrice, MathProperties math);
}
