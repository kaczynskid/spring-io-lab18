package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ItemRepositoryTest {

    @Autowired
    TestEntityManager jpa;

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
}
