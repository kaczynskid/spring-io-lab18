package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.math.BigDecimal.ZERO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Item {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private int count = 0;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal price = ZERO;

	void update(ItemUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		name = changes.getName();
		price = changes.getPrice();
	}

	void updateStock(ItemStockUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");
		Validate.isTrue(changes.getCountDiff() != 0, "CountDiff cannot be zero");

		this.count = changes.applyFor(this);
	}
}
