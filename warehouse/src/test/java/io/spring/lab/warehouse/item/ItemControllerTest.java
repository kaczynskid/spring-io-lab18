package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static io.spring.lab.warehouse.WarehousePersistenceConfig.testItemsData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ItemService items;

    @Test
    public void shouldGetAllItems() throws Exception {
        doReturn(testItemsData())
                .when(items).findAll();

        mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].name").value("A"))
                .andExpect(jsonPath("$.[0].price").value("40.0"));
    }

    @Test
    public void shouldGetItemById() throws Exception {
        doReturn(new Item(5L, "test", 100, BigDecimal.valueOf(13.5)))
                .when(items).findOne(5L);

        mvc.perform(get("/items/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.price").value("13.5"));
    }

    @Test
    public void shouldCreateItem() throws Exception {
        doReturn(new Item(5L, "test", 100, BigDecimal.valueOf(13.5)))
                .when(items).create(any(Item.class));

        mvc.perform(post("/items").contentType(APPLICATION_JSON_UTF8)
                .content("{\"name\": \"test\", \"count\": 100, \"price\": 13.5}"))
                .andExpect(status().isCreated());
    }
}
