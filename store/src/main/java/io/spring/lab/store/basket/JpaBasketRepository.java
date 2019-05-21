package io.spring.lab.store.basket;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaBasketRepository implements BasketRepository {

    private final SpringDataBasketRepository baskets;

    @Override
    public boolean isEmpty() {
        return baskets.count() == 0;
    }

    @Override
    public long count() {
        return baskets.count();
    }

    @Override
    public Optional<Basket> findOne(long id) {
        return baskets.findById(id);
    }

    @Override
    public Basket save(Basket basket) {
        return baskets.save(basket);
    }

    @Override
    public Basket saveAndFlush(Basket basket) {
        return baskets.saveAndFlush(basket);
    }

    interface SpringDataBasketRepository extends JpaRepository<Basket, Long> {
    }
}
