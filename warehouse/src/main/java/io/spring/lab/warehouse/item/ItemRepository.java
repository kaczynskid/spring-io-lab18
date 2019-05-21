package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;

import static io.spring.lab.warehouse.item.QItem.item;

public interface ItemRepository {

    static BooleanExpression withNameStartingFrom(String prefix) {
        return item.name.startsWith(prefix);
    }

    Optional<Item> findOne(long id);

    List<Item> findAll();

    Item save(Item item);

    Item findMostExpensive();

    List<Item> findByNamePrefix(String prefix);
}
