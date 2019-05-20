package io.spring.lab.marketing.special.select;

import java.math.BigDecimal;
import java.util.List;

import io.spring.lab.marketing.special.calculate.SpecialCalculator;

import static io.spring.lab.marketing.special.calculate.SpecialCalculator.regularPrice;

public class FirstSpecialSelector implements SpecialSelector {

	@Override
	public SpecialCalculator selectSpecial(List<SpecialCalculator> specials, int unitCount, BigDecimal unitPrice) {
		return specials.stream()
				.findFirst()
				.orElse(regularPrice);
	}
}
