package io.spring.lab.marketing.special;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.spring.lab.marketing.special.calculate.SpecialCalculator;
import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Repository
@AllArgsConstructor
public class MongoSpecialRepository implements SpecialRepository {

    private final SpringDataSpecialRepository specials;

    @Override
    public boolean isEmpty() {
        return specials.count() == 0;
    }

    @Override
    public long count() {
        return specials.count();
    }

    @Override
    public Optional<Special> findOne(String id) {
        return specials.findById(id);
    }

    @Override
    public List<Special> findAll() {
        return specials.findAll();
    }

    @Override
    public List<SpecialCalculator> findByItemIdAndBatchSizeLessThanEqual(long itemId, int batchSize) {
        return specials.findByItemIdAndBatchSizeLessThanEqual(itemId, batchSize).stream()
                .map(SpecialCalculator.class::cast)
                .collect(toList());
    }

    @Override
    public List<Special> findByItemId(long itemId) {
        return specials.findByItemId(itemId);
    }

    @Override
    public Special save(Special special) {
        return specials.save(special);
    }

    interface SpringDataSpecialRepository extends MongoRepository<Special, String> {

        List<Special> findByItemId(long itemId);

        List<Special> findByItemIdAndBatchSizeLessThanEqual(long itemId, int batchSize);
    }
}
