package io.spring.lab.warehouse.item;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import io.spring.lab.warehouse.WarehousePersistenceConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.FilterType.ANNOTATION;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = {
        @ComponentScan.Filter(type = ASSIGNABLE_TYPE, classes = WarehousePersistenceConfig.class),
        @ComponentScan.Filter(type = ANNOTATION, classes = Repository.class)
})
@AutoConfigureTestDatabase
public class ItemRepositoryTest {

    @Autowired
    TestEntityManager jpa;

    @Autowired
    ItemRepository items;

    @Test
    public void shouldFindNewlyCreatedItem() {
        // given
        Item entity = new Item(null, "E", 99, BigDecimal.valueOf(33.3));
        jpa.persistAndFlush(entity);
        jpa.clear();

        // when
        Item item = jpa.find(Item.class, entity.getId());

        // then
        assertThat(item).isNotNull();
        assertThat(item.getId()).isGreaterThan(0L);
        assertThat(item.getName()).isEqualTo("E");
        assertThat(item.getCount()).isEqualTo(99);
        assertThat(item.getPrice()).isEqualTo(BigDecimal.valueOf(3330, 2));
    }

    @Test
    public void shouldFindExistingItem() {
        // when
        Optional<Item> theOne = items.findOne(1L);

        // then
        assertThat(theOne).isPresent();
        theOne.ifPresent(item -> {
            assertThat(item.getName()).isEqualTo("A");
            assertThat(item.getCount()).isEqualTo(100);
            assertThat(item.getPrice()).isEqualTo(BigDecimal.valueOf(3000, 2));
        });
    }

    @Test
    public void shouldFindMostExpensiveItem() {
        // when
        Item item = items.findMostExpensive();

        // then
        assertThat(item).isNotNull();
        assertThat(item.getId()).isEqualTo(3L);
        assertThat(item.getPrice()).isEqualTo(BigDecimal.valueOf(4000, 2));
    }
}
