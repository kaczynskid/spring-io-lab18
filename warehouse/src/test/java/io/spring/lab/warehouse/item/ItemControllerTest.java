package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.spring.lab.warehouse.error.DefaultErrorController;

import static io.spring.lab.warehouse.WarehousePersistenceConfig.testItemsData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({
        DefaultErrorController.class,
        ItemController.class
})
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
                .andExpect(jsonPath("$.[0].price").value("30.0"));
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
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/items/5"));
    }

    @Test
    public void shouldReturn404WhenItemNotFound() throws Exception {
        doThrow(new ItemNotFound(5))
                .when(items).findOne(5L);

        mvc.perform(get("/items/{id}", 5L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Item 5 does not exist"));
    }

    @Test
    public void shouldReturn400WhenOutOfStock() throws Exception {
        Item item = new Item(5L, "test", 5, BigDecimal.valueOf(13.5));

        doThrow(new OutOfStock(item, -10))
                .when(items).updateStock(ItemStockUpdate.of(-10).withId(item.getId()));

        mvc.perform(put("/items/{id}/stock", item.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content("{\"countDiff\": -10}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Item test has only 5 out of -10 requested"));
    }

    @Test
    public void shouldReturn500WhenInternalError() throws Exception {
        mvc.perform(get("/error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Unexpected error"));
    }
}
