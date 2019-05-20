package io.spring.lab.store.special;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@AllArgsConstructor(access = PRIVATE)
public class SpecialCalculationRequest {

    private BigDecimal unitPrice;
    private int unitCount;

    public static SpecialCalculationRequest requestCalculationFor(BigDecimal unitPrice, int unitCount) {
        return new SpecialCalculationRequest(unitPrice, unitCount);
    }
}
