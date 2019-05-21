package io.spring.lab.store.basket;

import java.math.BigDecimal;

import org.junit.Test;
import org.mockito.Mockito;

import io.spring.lab.math.MathProperties;
import io.spring.lab.store.basket.item.BasketItem;
import io.spring.lab.store.basket.item.BasketItemRepository;
import io.spring.lab.store.basket.item.BasketItemService;
import io.spring.lab.store.basket.item.StubBasketItemRepository;
import io.spring.lab.store.item.ItemRepresentation;
import io.spring.lab.store.item.ItemsClient;
import io.spring.lab.store.special.SpecialCalculation;
import io.spring.lab.store.special.SpecialClient;

import static io.spring.lab.store.special.SpecialCalculationRequest.requestCalculationFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

public class BasketServiceStubTest {

    static final long ITEM_ID = 11L;
    static final String ITEM_NAME = "Coffee Mug";
    static final BigDecimal ITEM_UNIT_PRICE = BigDecimal.valueOf(123.5);
    static final int ITEM_REGULAR_COUNT = 2;
    static final BigDecimal ITEM_REGULAR_PRICE = BigDecimal.valueOf(247.0);
    static final int ITEM_SPECIAL_COUNT = 5;
    static final BigDecimal ITEM_SPECIAL_PRICE = BigDecimal.valueOf(524.87);
    static final String SPECIAL_ID = "promo-15-off";

    BasketItemRepository basketItemsRepo = new StubBasketItemRepository();

    ItemsClient items = Mockito.mock(ItemsClient.class);

    SpecialClient specials = Mockito.mock(SpecialClient.class);

    MathProperties math = new MathProperties();

    BasketItemService basketItems = new BasketItemService(basketItemsRepo, items, specials, math);

    BasketRepository basketsRepo = new StubBasketRepository();

    BasketService baskets = new BasketService(basketsRepo, basketItems, math);

    @Test
    public void shouldUpdateBasketWithRegularPriceItem() {
        // given
        Basket basket = baskets.create();
        doReturn(new ItemRepresentation(ITEM_NAME, ITEM_UNIT_PRICE))
                .when(items).findOne(ITEM_ID);
        doReturn(new SpecialCalculation(null, ITEM_REGULAR_PRICE))
                .when(specials).calculateFor(ITEM_ID, requestCalculationFor(ITEM_UNIT_PRICE, ITEM_REGULAR_COUNT));

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
        doReturn(new ItemRepresentation(ITEM_NAME, ITEM_UNIT_PRICE))
                .when(items).findOne(ITEM_ID);
        doReturn(new SpecialCalculation(SPECIAL_ID, ITEM_SPECIAL_PRICE))
                .when(specials).calculateFor(ITEM_ID, requestCalculationFor(ITEM_UNIT_PRICE, ITEM_SPECIAL_COUNT));

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
