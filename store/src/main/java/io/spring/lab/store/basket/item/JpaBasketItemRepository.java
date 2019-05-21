package io.spring.lab.store.basket.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaBasketItemRepository implements BasketItemRepository {

    private final SpringDataBasketItemRepository basketItems;

    @Override
    public boolean isEmpty() {
        return basketItems.count() == 0;
    }

    @Override
    public long count() {
        return basketItems.count();
    }

    @Override
    public List<BasketItem> findByBasketId(long basketId) {
        return basketItems.findByBasketId(basketId);
    }

    @Override
    public Optional<BasketItem> findByBasketIdAndItemId(long basketId, long itemId) {
        return basketItems.findByBasketIdAndItemId(basketId, itemId);
    }

    @Override
    public BasketItem save(BasketItem basketItem) {
        return basketItems.save(basketItem);
    }

    @Override
    public BasketItem saveAndFlush(BasketItem basketItem) {
        return basketItems.saveAndFlush(basketItem);
    }

    @Override
    public void delete(BasketItem basketItem) {
        basketItems.delete(basketItem);
    }

    interface SpringDataBasketItemRepository extends JpaRepository<BasketItem, Long> {

        List<BasketItem> findByBasketId(long basketId);

        Optional<BasketItem> findByBasketIdAndItemId(long basketId, long itemId);
    }
}
