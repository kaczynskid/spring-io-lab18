package io.spring.lab.store.basket

import io.spring.lab.math.MathProperties
import io.spring.lab.store.basket.item.BasketItem
import io.spring.lab.store.basket.item.BasketItemRepository
import io.spring.lab.store.basket.item.BasketItemService
import io.spring.lab.store.basket.item.StubBasketItemRepository
import io.spring.lab.store.item.ItemRepresentation
import io.spring.lab.store.item.ItemsClient
import io.spring.lab.store.special.SpecialCalculation
import io.spring.lab.store.special.SpecialClient
import spock.lang.Specification
import spock.lang.Unroll

import static io.spring.lab.store.basket.BasketStatus.CLOSED
import static io.spring.lab.store.basket.BasketStatus.OPEN
import static io.spring.lab.store.special.SpecialCalculationRequest.requestCalculationFor
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class BasketServiceSpec extends Specification {

    MathProperties math = new MathProperties()

    ItemsClient items = mock(ItemsClient)

    SpecialClient specials = mock(SpecialClient)

    BasketItemRepository basketItemsRepository = new StubBasketItemRepository()

    BasketItemService basketItems = new BasketItemService(basketItemsRepository, items, specials, math)

    BasketRepository basketsRepository = new StubBasketRepository()

    BasketService baskets = new BasketService(basketsRepository, basketItems, math)

    @Unroll
    def "Should update basket when item updated"() {
        given:
            when(items.findOne(1L))
                    .thenReturn(new ItemRepresentation('A', 5.0))
            when(specials.calculateFor(1L, requestCalculationFor(5.0, newCount)))
                    .thenReturn(new SpecialCalculation('S', newPrice))
        and:
            Basket basket = basketsRepository.saveAndFlush(new Basket(null, OPEN, totalCount, totalPrice))
            basketItemsRepository.saveAndFlush(new BasketItem(null, basket.id, 1L, 'A', 5.0, totalCount, totalPrice, 'S'))

        when:
            baskets.updateItem(basket.id, 1L, countDiff)

        then:
            basket.totalCount == newCount
            basket.totalPrice == newPrice

        where:
            totalCount | totalPrice | countDiff | priceDiff | newCount | newPrice
            5          | 70.0       | -1        | -10.0     |  4       |  60.0
            5          | 50.0       | -5        | -50.0     |  0       |   0.0
            5          | 50.0       |  5        |  50.0     | 10       | 100.0
            5          | 50.0       |  1        |   0.0     |  6       |  50.0
            5          | 50.0       | -1        |   0.0     |  4       |  50.0
            5          | 50.0       |  0        |  10.0     |  5       |  60.0
            5          | 50.0       |  0        | -10.0     |  5       |  40.0
            5          | 50.0       |  0        | -50.0     |  5       |   0.0
            5          | 50.0       | -5        |   0.0     |  0       |   0.0
    }

    def "Should not check out empty basket"() {
        given:
            Basket basket = baskets.create()

        when:
            baskets.checkout(basket.id)

        then:
            def ex = thrown(EmptyBasket)
            ex.message == "Basket ${basket.id} is empty"
    }

    def "Should check out basket"() {
        given:
           Basket basket = basketsRepository.saveAndFlush(new Basket(null, OPEN, 5, 15.0))

        when:
            baskets.checkout(basket.id)

        then:
            basket.status == CLOSED
            basket.totalCount == 5
            basket.totalPrice == 15.0
    }

    def "Should not check out basket more than once"() {
        given:
            Basket basket = basketsRepository.saveAndFlush(new Basket(null, CLOSED, 5, 15.0))
        when:
            baskets.checkout(basket.id)
        then:
            def ex = thrown(AlreadyCheckedOut)
            ex.message == "Basket ${basket.id} is already checked out"
    }
}