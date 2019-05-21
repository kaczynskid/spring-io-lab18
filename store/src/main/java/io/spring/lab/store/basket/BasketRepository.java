package io.spring.lab.store.basket;

import java.util.Optional;

public interface BasketRepository {

    boolean isEmpty();

    long count();

    Optional<Basket> findOne(long id);

    Basket save(Basket basket);

    Basket saveAndFlush(Basket basket);
}
