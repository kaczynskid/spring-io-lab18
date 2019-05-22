package io.spring.lab.store.basket.item;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemRepresentation {

	@JsonIgnore
	private Long itemId;

	private String name;

	private int count;

	private BigDecimal price;

	public static BasketItemRepresentation of(BasketItem item) {
		return new BasketItemRepresentation(item.getItemId(), item.getName(), item.getUnitCount(), item.getTotalPrice());
	}
}
