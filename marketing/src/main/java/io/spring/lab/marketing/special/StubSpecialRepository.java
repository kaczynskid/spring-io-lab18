package io.spring.lab.marketing.special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.spring.lab.marketing.special.calculate.SpecialCalculator;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

class StubSpecialRepository implements SpecialRepository {

    private final Map<String, Special> db = new HashMap<>();

    @Override
    public Special findOne(String id) {
        return db.get(id);
    }

    @Override
    public List<Special> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public List<SpecialCalculator> findByItemIdAndBatchSizeLessThanEqual(long itemId, int batchSize) {
        return db.values().stream()
                .filter(it -> it.getItemId() == itemId && it.getBatchSize() <= batchSize)
                .collect(toList());
    }

    @Override
    public List<Special> findByItemId(long itemId) {
        return db.values().stream()
                .filter(it -> it.getItemId() == itemId)
                .collect(toList());
    }

    @Override
    public Special save(Special special) {
        String id = setAndGetNextId(special);
        db.put(id, special);
        return special;
    }

    private String setAndGetNextId(Special special) {
        try {
            String id = ofNullable(special.getId()).orElseGet(() -> UUID.randomUUID().toString().replaceAll("-", ""));
            writeField(special, "id", id, true);
            return id;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected error!", e);
        }
    }
}
