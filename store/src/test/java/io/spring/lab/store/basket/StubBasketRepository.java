package io.spring.lab.store.basket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.test.context.TestComponent;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

@TestComponent
class StubBasketRepository implements BasketRepository {

    private final AtomicLong seq = new AtomicLong();
    private final Map<Long, Basket> db = new HashMap<>();

    @Override
    public boolean isEmpty() {
        return db.isEmpty();
    }

    @Override
    public long count() {
        return db.size();
    }

    @Override
    public Optional<Basket> findOne(long id) {
        return ofNullable(db.get(id));
    }

    @Override
    public Basket save(Basket basket) {
        long id = ofNullable(basket.getId())
                .orElseGet(() -> generateAndSetId(basket));
        db.put(id, basket);
        return basket;
    }

    @Override
    public Basket saveAndFlush(Basket basket) {
        return save(basket);
    }

    private long generateAndSetId(Basket basket) {
        try {
            long id = seq.incrementAndGet();
            writeField(basket, "id", id, true);
            return id;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected error!", e);
        }
    }
}
