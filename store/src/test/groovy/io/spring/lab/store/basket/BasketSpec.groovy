package io.spring.lab.store.basket

import io.spring.lab.math.MathProperties
import spock.lang.Specification
import spock.lang.Unroll

import static io.spring.lab.store.basket.BasketStatus.CLOSED
import static io.spring.lab.store.basket.BasketStatus.OPEN

class BasketSpec extends Specification {

    MathProperties math = new MathProperties()

    @Unroll
    def "Should not update with invalid diff"() {
        given:
            Basket basket = new Basket(1L, status, totalCount, totalPrice)
        when:
            basket.update(new BasketUpdateDiff(countDiff, priceDiff), math)
        then:
            def ex = thrown(error)
            ex.message == message
        where:
            status | totalCount | totalPrice | countDiff | priceDiff | error                    | message
            OPEN   | 0          |  0.0       | -1        | -10.0     | IllegalArgumentException | 'Basket total count cannot be negative'
            OPEN   | 5          | 70.0       | -6        | -60.0     | IllegalArgumentException | 'Basket total count cannot be negative'
            OPEN   | 5          | 50.0       | -5        | -60.0     | IllegalArgumentException | 'Basket total price cannot be negative'
            CLOSED | 5          | 50.0       |  5        |  50.0     | AlreadyCheckedOut        | 'Basket 1 is already checked out'
    }

    @Unroll
    def "Should update basket"() {
        given:
            Basket basket = new Basket(1L, OPEN, totalCount, totalPrice)
        when:
            basket.update(new BasketUpdateDiff(countDiff, priceDiff), math)
        then:
            basket.totalCount == newCount
            basket.totalPrice == newPrice
        where:
            totalCount | totalPrice | countDiff | priceDiff | newCount | newPrice
            0          |  0.0       |  1        |  10.0     |  1       |  10.0
            5          | 70.0       | -1        | -10.0     |  4       |  60.0
            5          | 50.0       | -5        | -50.0     |  0       |   0.0
            5          | 50.0       |  5        |  50.0     | 10       | 100.0
            5          | 50.0       |  1        |   0.0     |  6       |  50.0
            5          | 50.0       | -1        |   0.0     |  4       |  50.0
            5          | 50.0       |  0        |  10.0     |  5       |  60.0
            5          | 50.0       |  0        | -10.0     |  5       |  40.0
            5          | 50.0       |  0        | -50.0     |  5       |   0.0
            5          | 50.0       | -5        |   0.0     |  0       |  50.0
    }

    def "Should not check out empty basket"() {
        given:
            Basket basket = new Basket(1L)
        when:
            basket.checkout()
        then:
            def ex = thrown(EmptyBasket)
            ex.message == 'Basket 1 is empty'
    }

    def "Should check out basket"() {
        given:
            Basket basket = new Basket(1L, OPEN, 5, 15.0)
        when:
            basket.checkout()
        then:
            basket.id == 1L
            basket.status == CLOSED
            basket.totalCount == 5
            basket.totalPrice == 15.0
    }

    def "Should not check out basket more than once"() {
        given:
            Basket basket = new Basket(1L, CLOSED, 5, 15.0)
        when:
            basket.checkout()
        then:
            def ex = thrown(AlreadyCheckedOut)
            ex.message == 'Basket 1 is already checked out'
    }
}
