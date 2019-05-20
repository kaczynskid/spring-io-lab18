package io.spring.lab.store.special;

public interface SpecialClient {

    SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request);

}
