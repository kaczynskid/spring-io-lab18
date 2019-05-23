package io.spring.lab.store.special;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import io.spring.lab.cloud.ConditionalOnFeignClient;
import io.spring.lab.stereotype.WebClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@WebClient
@AllArgsConstructor
@ConditionalOnFeignClient
public class DeclarativeSpecialClient implements SpecialClient {

    private final FeignSpecialClient specials;

    @Override
    public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
        SpecialCalculation calculation = specials.calculateFor(APPLICATION_JSON_UTF8_VALUE, itemId, request);
        log.info("Declarative client got special calculation: {} ({})",
                calculation.getTotalPrice(),
                Optional.ofNullable(calculation.getSpecialId()).orElse("regular"));
        return calculation;
    }

    @FeignClient(name = "marketing", path = "/specials")
    interface FeignSpecialClient {

        @PostMapping("/{itemId}/calculate")
        SpecialCalculation calculateFor(
                @RequestHeader("Accept") String accept,
                @PathVariable("itemId") long itemId,
                @RequestBody SpecialCalculationRequest request);
    }
}
