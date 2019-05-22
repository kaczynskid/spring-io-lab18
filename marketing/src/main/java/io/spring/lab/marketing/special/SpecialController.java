package io.spring.lab.marketing.special;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.lab.marketing.special.calculate.SpecialCalculation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/specials")
public class SpecialController {

    private final SpecialService specials;

    @GetMapping
    public List<SpecialRepresentation> findAll() {
        return specials.findAll().stream()
                .map(SpecialRepresentation::of)
                .collect(toList());
    }

    @PostMapping("/{itemId}/calculate")
    public SpecialCalculation calculateFor(@PathVariable("itemId") long itemId,
                                           @RequestBody SpecialCalculationRequest request) {
        SpecialCalculation calculation = specials.calculateFor(itemId, request.getUnitPrice(), request.getUnitCount());
        log.info("Calculated special {}", calculation);
        return calculation;
    }
}
