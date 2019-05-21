package io.spring.lab.store.item;

import lombok.Value;

@Value
public class ItemStockUpdate {

	private long id;

	private int countDiff;
}
