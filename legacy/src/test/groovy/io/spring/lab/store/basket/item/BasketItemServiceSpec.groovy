package io.spring.lab.store.basket.item

import io.spring.lab.math.MathProperties
import io.spring.lab.store.basket.Basket
import io.spring.lab.store.basket.BasketRepository
import io.spring.lab.store.basket.BasketUpdateDiff
import io.spring.lab.store.basket.StubBasketRepository
import io.spring.lab.store.item.ItemRepresentation
import io.spring.lab.store.item.ItemStockUpdate
import io.spring.lab.store.item.ItemsClient
import io.spring.lab.store.special.SpecialCalculation
import io.spring.lab.store.special.SpecialClient
import spock.lang.Specification

import static io.spring.lab.store.special.SpecialCalculationRequest.requestCalculationFor
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.verifyZeroInteractions
import static org.mockito.Mockito.when

class BasketItemServiceSpec extends Specification {

    MathProperties math = new MathProperties()

    ItemsClient items = mock(ItemsClient)

    SpecialClient specials = mock(SpecialClient)

    BasketItemRepository basketItemsRepository = new StubBasketItemRepository()

    BasketRepository baskets = new StubBasketRepository()

    BasketItemService basketItems = new BasketItemService(basketItemsRepository, items, specials, math)

    def "Should add new item"() {
        given:
            when(items.findOne(6L))
                    .thenReturn(new ItemRepresentation('A', 5.0))
            when(specials.calculateFor(6L, requestCalculationFor(5.0, 5)))
                    .thenReturn(new SpecialCalculation('S', 8.0))
        and:
            Basket basket = baskets.saveAndFlush(new Basket())

        when:
            BasketUpdateDiff diff = basketItems.updateItem(basket.id, 6L, 5)

        then:
            diff.countDiff == 5
            diff.priceDiff == 8.0
    }

    def "Should update existing item"() {
        given:
            when(items.findOne(6L))
                    .thenReturn(new ItemRepresentation('A', 5.0))
            when(specials.calculateFor(6L, requestCalculationFor(5.0, 7)))
                    .thenReturn(new SpecialCalculation('S', 13.0))
        and:
            Basket basket = new Basket()
            basket.update(new BasketUpdateDiff(5, 8.0), math)
            baskets.saveAndFlush(basket)
            basketItemsRepository.saveAndFlush(new BasketItem(null, basket.id, 6L, 'A', 5.0, 5, 8.0, 'S'))

        when:
            BasketUpdateDiff diff = basketItems.updateItem(basket.id, 6L, 2)

        then:
            diff.countDiff == 2
            diff.priceDiff == 5.0
    }

    def "Should remove existing item when count reaches zero"() {
        given:
            Basket basket = new Basket()
            basket.update(new BasketUpdateDiff(5, 8.0), math)
            baskets.saveAndFlush(basket)
            basketItemsRepository.saveAndFlush(new BasketItem(null, basket.id, 6L, 'A', 5.0, 5, 8.0, 'S'))

        when:
            BasketUpdateDiff diff = basketItems.updateItem(basket.id, 6L, -5)

        then:
            diff.countDiff == -5
            diff.priceDiff == -8.0
            verifyZeroInteractions(items, specials)
    }

    def "Should checkout items"() {
        given:
            Basket basket = new Basket()
            basket.update(new BasketUpdateDiff(5, 8.0), math)
            baskets.saveAndFlush(basket)
            basketItemsRepository.saveAndFlush(new BasketItem(null, basket.id, 6L, 'A', 5.0, 5, 8.0, 'S'))

        when:
            basketItems.checkout(basket.id)

        then:
            verify(items).updateStock(new ItemStockUpdate(6L, -5))
    }
}
