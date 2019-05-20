package io.spring.lab.store.item;

import java.util.List;

import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemService;
import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class ServiceItemsClient implements ItemsClient {

    private ItemService items;

    @Override
    public List<ItemRepresentation> findAll() {
        return items.findAll().stream()
                .map(this::asItemRepresentation)
                .collect(toList());
    }

    @Override
    public ItemRepresentation findOne(long id) {
        return asItemRepresentation(items.findOne(id));
    }

    @Override
    public void updateStock(ItemStockUpdate changes) {
        io.spring.lab.warehouse.item.ItemStockUpdate update =
                io.spring.lab.warehouse.item.ItemStockUpdate.of(changes.getCountDiff()).withId(changes.getId());
        items.updateStock(update);
    }

    private ItemRepresentation asItemRepresentation(Item item) {
        return new ItemRepresentation(item.getName(), item.getPrice());
    }
}
