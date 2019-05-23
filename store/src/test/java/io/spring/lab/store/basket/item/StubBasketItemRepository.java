package io.spring.lab.store.basket.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.test.context.TestComponent;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

@TestComponent
public class StubBasketItemRepository implements BasketItemRepository {

    private final AtomicLong seq = new AtomicLong();
    private final Map<Long, BasketItem> db = new HashMap<>();

    @Override
    public boolean isEmpty() {
        return db.isEmpty();
    }

    @Override
    public long count() {
        return db.size();
    }

    @Override
    public List<BasketItem> findByBasketId(long basketId) {
        return db.values().stream()
                .filter(it -> it.getBasketId() == basketId)
                .collect(toList());
    }

    @Override
    public Optional<BasketItem> findByBasketIdAndItemId(long basketId, long itemId) {
        return db.values().stream()
                .filter(it -> it.getBasketId() == basketId && it.getItemId() == itemId)
                .findFirst();
    }

    @Override
    public BasketItem save(BasketItem basketItem) {
        long id = ofNullable(basketItem.getId())
                .orElseGet(() -> generateAndSetId(basketItem));
        db.put(id, basketItem);
        return basketItem;
    }

    @Override
    public BasketItem saveAndFlush(BasketItem basketItem) {
        return save(basketItem);
    }

    @Override
    public void delete(BasketItem basketItem) {
        db.remove(basketItem.getId());
    }

    private long generateAndSetId(BasketItem basketItem) {
        try {
            long id = seq.incrementAndGet();
            writeField(basketItem, "id", id, true);
            return id;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected error!", e);
        }
    }
}
