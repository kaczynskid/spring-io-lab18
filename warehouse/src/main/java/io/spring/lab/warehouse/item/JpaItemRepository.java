package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;

import static io.spring.lab.warehouse.item.ItemRepository.withNameStartingFrom;

@Repository
@AllArgsConstructor
public class JpaItemRepository implements ItemRepository {

    private final SpringDataItemRepository items;

    @Override
    public boolean isEmpty() {
        return items.count() == 0;
    }

    @Override
    public long count() {
        return items.count();
    }

    @Override
    public Optional<Item> findOne(long id) {
        return items.findById(id);
    }

    @Override
    public List<Item> findAll() {
        return items.findAll();
    }

    @Override
    public Item save(Item item) {
        return items.save(item);
    }

    @Override
    public Item findMostExpensive() {
        return items.findTopByOrderByPriceDesc();
    }

    @Override
    public List<Item> findByNamePrefix(String prefix) {
        //return items.findByNamePrefix(prefix);
        return items.findAll(withNameStartingFrom(prefix));
    }

    interface SpringDataItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

        Item findTopByOrderByPriceDesc();

        @Query("from Item where name like :prefix%")
        List<Item> findByNamePrefix(@Param("prefix") String namePrefix);

        @Override
        List<Item> findAll(Predicate predicate);
    }
}
