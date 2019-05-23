package io.spring.lab.store.item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRepresentation {

	private String name;

	private BigDecimal price;

	private String instanceId;

	public ItemRepresentation(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}
}
