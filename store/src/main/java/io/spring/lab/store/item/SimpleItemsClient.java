package io.spring.lab.store.item;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Slf4j
@Component
@AllArgsConstructor
public class SimpleItemsClient implements ItemsClient {

    private static final String BASE_URI = "http://warehouse";
    private static final String ITEM_URI = BASE_URI + "/items/{id}";
    private static final String ITEM_STOCK_URI = BASE_URI + "/items/{id}/stock";

    private final RestTemplate rest;

    @Override
    public ItemRepresentation findOne(long id) {
        ItemRepresentation representation = rest.exchange(
                ITEM_URI, GET, emptyEntity(), ItemRepresentation.class, id)
                .getBody();
        log.info("Simple client got item {} from instance {}", representation.getName(), representation.getInstanceId());
        return representation;
    }

    @Override
    public void updateStock(ItemStockUpdate changes) {

    }

    private HttpEntity<?> emptyEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON_UTF8));
        return new HttpEntity<>(headers);
    }

}
