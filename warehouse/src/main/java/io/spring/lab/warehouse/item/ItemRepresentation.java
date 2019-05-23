package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRepresentation {

	private String name;

	@JsonProperty("count")
	private int stock;

	private BigDecimal price;

	private String instanceId;

	static ItemRepresentation of(Item item) {
		return new ItemRepresentation(item.getName(), item.getCount(), item.getPrice(), null);
	}

	Item asItem() {
		return new Item(null, name, stock, price);
	}

	ItemRepresentation withInstanceId(String instanceId) {
		return new ItemRepresentation(name, stock, price, instanceId);
	}
}
