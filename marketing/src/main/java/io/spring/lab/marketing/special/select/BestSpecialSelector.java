package io.spring.lab.marketing.special.select;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import io.spring.lab.marketing.special.calculate.SpecialCalculator;
import io.spring.lab.math.MathProperties;

import static io.spring.lab.marketing.special.calculate.SpecialCalculator.regularPrice;
import static java.util.Comparator.comparing;

public class BestSpecialSelector implements SpecialSelector {

	private final MathProperties math;

	public BestSpecialSelector(MathProperties math) {
		this.math = math;
	}

	@Override
	public SpecialCalculator selectSpecial(List<SpecialCalculator> specials, int unitCount, BigDecimal unitPrice) {
		return Stream.concat(
					Stream.of(regularPrice),
					specials.stream())
				.min(comparing(s -> s.calculateFor(unitCount, unitPrice, math).getTotalPrice()))
				.orElse(regularPrice);
	}
}
