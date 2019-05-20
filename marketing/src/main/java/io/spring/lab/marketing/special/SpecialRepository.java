package io.spring.lab.marketing.special;

import java.util.List;

import io.spring.lab.marketing.special.calculate.SpecialCalculator;

public interface SpecialRepository {

	Special findOne(String id);

	List<Special> findAll();

	List<SpecialCalculator> findByItemIdAndBatchSizeLessThanEqual(long itemId, int batchSize);

	List<Special> findByItemId(long itemId);

	Special save(Special special);
}
