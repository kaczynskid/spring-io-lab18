package io.spring.lab.marketing.special.select;

import java.math.BigDecimal;
import java.util.List;

import io.spring.lab.marketing.special.calculate.SpecialCalculator;

@FunctionalInterface
public interface SpecialSelector {

	SpecialCalculator selectSpecial(List<SpecialCalculator> specials, int unitCount, BigDecimal unitPrice);
}
