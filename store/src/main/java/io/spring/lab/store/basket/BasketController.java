package io.spring.lab.store.basket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/baskets")
@AllArgsConstructor
public class BasketController {

	private final BasketService baskets;

	@PostMapping
	public BasketRepresentation create() {
		Basket basket = baskets.create();
		log.info("Created basket {}", basket.getId());
		return BasketRepresentation.of(basket);
	}

	@GetMapping("/{id}")
	public BasketRepresentation findOne(@PathVariable("id") long id) {
		return BasketRepresentation.of(baskets.findOne(id));
	}

	@PostMapping("/{id}/checkout")
	public BasketRepresentation checkout(@PathVariable("id") long id) {
		Basket basket = baskets.checkout(id);
		log.info("Checked out basket {}", basket.getId());
		return BasketRepresentation.of(basket);
	}
}
