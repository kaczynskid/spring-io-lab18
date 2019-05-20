package io.spring.lab.store.special;

import io.spring.lab.marketing.special.SpecialService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceSpecialClient implements SpecialClient {

    private SpecialService specials;

    @Override
    public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
        io.spring.lab.marketing.special.calculate.SpecialCalculation serviceCalculation =
                specials.calculateFor(itemId, request.getUnitPrice(), request.getUnitCount());
        return new SpecialCalculation(serviceCalculation.getSpecialId(), serviceCalculation.getTotalPrice());
    }
}
