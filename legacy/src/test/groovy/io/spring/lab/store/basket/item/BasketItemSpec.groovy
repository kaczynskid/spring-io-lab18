package io.spring.lab.store.basket.item

import io.spring.lab.math.MathProperties
import io.spring.lab.store.basket.BasketUpdateDiff
import io.spring.lab.store.item.ItemRepresentation
import io.spring.lab.store.special.SpecialCalculation
import spock.lang.Specification
import spock.lang.Unroll

class BasketItemSpec extends Specification {

    MathProperties math = new MathProperties()

    def "Should not update item with invalid data"() {
        given:
            BasketItem basketItem = new BasketItem(1L, 1L, 1L, 'test', 1.0, 5, 10.0, null)
        when:
            basketItem.update(null, 7,
                    new SpecialCalculation('5', 21.0), math)
        then:
            def ex = thrown(NullPointerException)
            ex.message == 'Item changes cannot be null'
        when:
            basketItem.update(new ItemRepresentation('test item', 4.0), 0,
                    new SpecialCalculation('5', 21.0), math)
        then:
            ex = thrown(IllegalArgumentException)
            ex.message == 'UnitCount must be positive'
        when:
            basketItem.update(new ItemRepresentation('test item', 4.0), 7,
                    null, math)
        then:
            ex = thrown(NullPointerException)
            ex.message == 'SpecialCalculation cannot be null'
        when:
            basketItem.update(new ItemRepresentation('test item', 4.0), 7,
                    new SpecialCalculation('5', 21.0), null)
        then:
            ex = thrown(NullPointerException)
            ex.message == 'Math cannot be null'
    }

    def "Should update item"() {
        given:
            BasketItem basketItem = new BasketItem(1L, 1L, 1L, 'test', 1.0, 5, 10.0, null)
        when:
            BasketUpdateDiff diff = basketItem.update(
                    new ItemRepresentation('test item', 4.0), 7,
                    new SpecialCalculation('5', 21.0), math)
        then:
            basketItem.id == 1L
            basketItem.basketId == 1L
            basketItem.itemId == 1L
            basketItem.name == 'test item'
            basketItem.unitPrice == 4.0
            basketItem.unitCount == 7
            basketItem.totalPrice == 21.0
            basketItem.specialId == '5'
            diff.countDiff == 2
            diff.priceDiff == 11.0
    }

    @Unroll
    def "Should update count and price of item"() {
        given:
            BasketItem basketItem = new BasketItem(1L, 1L, 1L, 'test', 1.0, 5, 10.0, null)
        when:
            BasketUpdateDiff diff = basketItem.update(
                new ItemRepresentation('test', 1.0), unitCount,
                new SpecialCalculation(null, totalPrice), math)
        then:
            basketItem.unitCount == unitCount
            basketItem.totalPrice == totalPrice
            diff.countDiff == countDiff
            diff.priceDiff == priceDiff
        where:
            unitCount | totalPrice | countDiff | priceDiff
            10        | 20.0       |  5        |  10.0
             1        |  2.0       | -4        |  -8.0
    }
}
