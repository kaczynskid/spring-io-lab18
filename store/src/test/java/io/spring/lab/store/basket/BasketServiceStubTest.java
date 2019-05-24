package io.spring.lab.store.basket;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import io.spring.lab.cloud.AutoConfigureFeign;
import io.spring.lab.cloud.AutoConfigureRibbon;
import io.spring.lab.math.AutoConfigureMath;
import io.spring.lab.repository.AutoConfigureStubRepositories;
import io.spring.lab.stereotype.WebClient;
import io.spring.lab.store.SpringTestBase;
import io.spring.lab.store.StoreCloudConfig;
import io.spring.lab.store.StoreRestTemplateConfig;
import io.spring.lab.store.basket.item.BasketItem;
import io.spring.lab.store.basket.item.BasketItemService;
import io.spring.lab.store.item.ItemStreamsConfig.ItemStreamsBinding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.FilterType.ANNOTATION;

@RestClientTest(components = {
        StoreRestTemplateConfig.class,
        StoreCloudConfig.StoreFeignClientsConfig.class,
        BasketItemService.class,
        BasketService.class
}, includeFilters = {
        @ComponentScan.Filter(type = ANNOTATION, classes = WebClient.class)
})
@AutoConfigureMath
@AutoConfigureFeign
@AutoConfigureRibbon
@AutoConfigureStubRepositories
public class BasketServiceStubTest extends SpringTestBase {

    static final long ITEM_ID = 11L;
    static final String ITEM_NAME = "Coffee Mug";
    static final int ITEM_REGULAR_COUNT = 2;
    static final BigDecimal ITEM_REGULAR_PRICE = BigDecimal.valueOf(247.0);
    static final int ITEM_SPECIAL_COUNT = 5;
    static final BigDecimal ITEM_SPECIAL_PRICE = BigDecimal.valueOf(524.87);
    static final String SPECIAL_ID = "promo-15-off";

    @MockBean
    ItemStreamsBinding binding;

    @Autowired
    BasketItemService basketItems;

    @Autowired
    BasketService baskets;

    @Test
    public void shouldUpdateBasketWithRegularPriceItem() {
        // given
        Basket basket = baskets.create();

        // when
        BasketUpdateDiff diff = baskets.updateItem(basket.getId(), ITEM_ID, ITEM_REGULAR_COUNT);

        // then
        assertThat(diff).isNotNull();
        assertThat(diff.getCountDiff()).isEqualTo(ITEM_REGULAR_COUNT);
        assertThat(diff.getPriceDiff()).isEqualByComparingTo(ITEM_REGULAR_PRICE);

        // and
        BasketItem basketItem = basketItems.findOneItem(basket.getId(), ITEM_ID);
        assertThat(basketItem.getName()).isEqualTo(ITEM_NAME);
        assertThat(basketItem.getTotalPrice()).isEqualByComparingTo(ITEM_REGULAR_PRICE);
        assertThat(basketItem.getSpecialId()).isNull();
    }

    @Test
    public void shouldUpdateBasketWithSpecialPriceItem() {
        // given
        Basket basket = baskets.create();

        // when
        BasketUpdateDiff diff = baskets.updateItem(basket.getId(), ITEM_ID, ITEM_SPECIAL_COUNT);

        // then
        assertThat(diff).isNotNull();
        assertThat(diff.getCountDiff()).isEqualTo(ITEM_SPECIAL_COUNT);
        assertThat(diff.getPriceDiff()).isEqualByComparingTo(ITEM_SPECIAL_PRICE);

        // and
        BasketItem basketItem = basketItems.findOneItem(basket.getId(), ITEM_ID);
        assertThat(basketItem.getName()).isEqualTo(ITEM_NAME);
        assertThat(basketItem.getTotalPrice()).isEqualByComparingTo(ITEM_SPECIAL_PRICE);
        assertThat(basketItem.getSpecialId()).isEqualTo(SPECIAL_ID);
    }
}
