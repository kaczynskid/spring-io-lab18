package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode
@ToString
class ItemUpdate {

	private final long id;

	private final String name;

	private final BigDecimal price;

	static ItemUpdate of(String name, BigDecimal price) {
		return new ItemUpdate(0, name, price);
	}

	ItemUpdate withId(long id) {
		return new ItemUpdate(id, name, price);
	}
}
