package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JpaItemRepository implements ItemRepository {

    private final SpringDataItemRepository items;

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

    interface SpringDataItemRepository extends JpaRepository<Item, Long> {
    }
}
