package io.spring.lab.warehouse.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

@Component
class StubItemRepository implements ItemRepository {

    private final AtomicLong seq = new AtomicLong();
    private final Map<Long, Item> db = new HashMap<>();

    @Override
    public Optional<Item> findOne(long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Item save(Item item) {
        long id = setAndGetNextId(item);
        db.put(id, item);
        return item;
    }

    private long setAndGetNextId(Item item) {
        try {
            long id = ofNullable(item.getId()).orElseGet(seq::incrementAndGet);
            writeField(item, "id", id, true);
            return id;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected error!", e);
        }
    }

}
