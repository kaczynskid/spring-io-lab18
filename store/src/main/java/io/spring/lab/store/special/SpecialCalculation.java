package io.spring.lab.store.special;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialCalculation {

	private String specialId;

	private BigDecimal totalPrice;
}
