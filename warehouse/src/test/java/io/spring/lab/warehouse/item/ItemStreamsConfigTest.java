package io.spring.lab.warehouse.item;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

import io.spring.lab.warehouse.StubRunnerTestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class ItemStreamsConfigTest extends StubRunnerTestBase {

    @Autowired
    StubTrigger stubs;

    @Autowired
    ItemService items;

    @Test
    public void shouldUpdateStock() {
        int initialStock = items.findOne(ITEM_ID).getCount();

        stubs.trigger("update_stock");

        int currentStock = items.findOne(ITEM_ID).getCount();

        assertThat(currentStock).isLessThan(initialStock);
        assertThat(initialStock - currentStock).isEqualTo(ITEM_REGULAR_COUNT);
    }
}
