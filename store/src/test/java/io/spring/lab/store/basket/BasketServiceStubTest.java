package io.spring.lab.store.basket;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

import io.spring.lab.math.MathProperties;
import io.spring.lab.store.basket.item.BasketItem;
import io.spring.lab.store.basket.item.BasketItemRepository;
import io.spring.lab.store.basket.item.BasketItemService;
import io.spring.lab.store.basket.item.StubBasketItemRepository;
import io.spring.lab.store.item.ItemsClient;
import io.spring.lab.store.item.SimpleItemsClient;
import io.spring.lab.store.special.SimpleSpecialClient;
import io.spring.lab.store.special.SpecialClient;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@RestClientTest(components = {
        SimpleItemsClient.class,
        SimpleSpecialClient.class
})
@AutoConfigureStubRunner(ids = {
        "io.spring.lab:warehouse",
        "io.spring.lab:marketing"
}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@AutoConfigureWebClient(registerRestTemplate = true)
@AutoConfigureMockRestServiceServer(enabled = false)
public class BasketServiceStubTest {

    static final long ITEM_ID = 11L;
    static final String ITEM_NAME = "Coffee Mug";
    static final int ITEM_REGULAR_COUNT = 2;
    static final BigDecimal ITEM_REGULAR_PRICE = BigDecimal.valueOf(247.0);
    static final int ITEM_SPECIAL_COUNT = 5;
    static final BigDecimal ITEM_SPECIAL_PRICE = BigDecimal.valueOf(524.87);
    static final String SPECIAL_ID = "promo-15-off";

    BasketItemRepository basketItemsRepo = new StubBasketItemRepository();

    @Autowired
    ItemsClient items;

    @Autowired
    SpecialClient specials;

    MathProperties math = new MathProperties();

    BasketItemService basketItems;

    BasketRepository basketsRepo = new StubBasketRepository();

    BasketService baskets;

    @Before
    public void setUp() {
        basketItems = new BasketItemService(basketItemsRepo, items, specials, math);
        baskets = new BasketService(basketsRepo, basketItems, math);
    }

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
