package io.spring.lab.store.special;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.spring.lab.cloud.ConditionalOnMissingFeignClient;
import io.spring.lab.stereotype.WebClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebClient
@AllArgsConstructor
@ConditionalOnMissingFeignClient
public class SimpleSpecialClient implements SpecialClient {

    private static final String BASE_URI = "http://marketing";
    private static final String SPECIAL_CALCULATION_URI = BASE_URI + "/specials/{itemId}/calculate";

    private final RestTemplate rest;

    @Override
    public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
        SpecialCalculation calculation = rest.postForObject(SPECIAL_CALCULATION_URI, request, SpecialCalculation.class, itemId);
        log.info("Simple client got special calculation: {} ({})",
                calculation.getTotalPrice(),
                Optional.ofNullable(calculation.getSpecialId()).orElse("regular"));
        return calculation;
    }
}

