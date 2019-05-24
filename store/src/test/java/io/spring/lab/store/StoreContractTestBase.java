package io.spring.lab.store;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;

import io.spring.lab.store.basket.Basket;
import io.spring.lab.store.basket.BasketService;
import io.spring.lab.store.item.ItemStreamsConfig;

import static io.spring.lab.store.item.ItemStreamsConfig.CHECKOUT_ITEM;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public abstract class StoreContractTestBase extends SpringDbTestBase {

    static final long ITEM_ID = 11L;
    static final int ITEM_REGULAR_COUNT = 2;

    @Autowired
    MessageVerifier messaging;

    @Autowired
    BasketService baskets;

    @Before
    public void setUp() {
        // clear any remaining messages
        messaging.receive(CHECKOUT_ITEM, 100, MILLISECONDS);
    }

    public void basketCheckout() {
        Basket basket = baskets.create();
        baskets.updateItem(basket.getId(), ITEM_ID, ITEM_REGULAR_COUNT);
        baskets.checkout(basket.getId());
    }
}
