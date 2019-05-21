package io.spring.lab.warehouse.item;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ItemService {

	private final ItemRepository items;
	private final MeterRegistry meters;

	@PostConstruct
	public void init() {
		meters.gauge("warehouse.items.count", tagsFor("read"), items, ItemRepository::count);
	}

	public Item findOne(long id) {
		increment(id + ".get", "read");
		return items.findOne(id)
				.orElseThrow(() -> new ItemNotFound(id));
	}

	public List<Item> findAll() {
		increment("get", "read");
		return items.findAll();
	}

	public Item create(Item item) {
		increment("create", "create");
		return items.save(item);
	}

	public Item update(ItemUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		Item item = findOne(changes.getId());
		item.update(changes);
		increment(item.getId() + ".update", "update");
		return items.save(item);
	}

	public Item updateStock(ItemStockUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		Item item = findOne(changes.getId());
		item.updateStock(changes);
		increment(item.getId() + ".stock.update", "update");
		return items.save(item);
	}

	private void increment(String name, String operation) {
		meters.counter("warehouse.items." + name, tagsFor(operation)).increment();
	}

	private static Iterable<Tag> tagsFor(String operation) {
		return Arrays.asList(
				Tag.of("service", "warehouse"),
				Tag.of("domain", "item"),
				Tag.of("operation", operation)
		);
	}
}
