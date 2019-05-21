package io.spring.lab.warehouse;

import java.math.BigDecimal;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemController;
import io.spring.lab.warehouse.item.ItemService;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public abstract class WarehouseContractTestBase {

    @Before
    public void setUp() {
        MockMvc mvc = standaloneSetup(prepareItemController()).build();
        RestAssuredMockMvc.mockMvc(mvc);
    }

    private ItemController prepareItemController() {
        ItemService items = mock(ItemService.class);

        doReturn(coffeeMug())
                .when(items).findOne(11L);

        return new ItemController(items);
    }

    private Item coffeeMug() {
        return new Item(11L, "Coffee Mug", 100, BigDecimal.valueOf(123.5));
    }
}
