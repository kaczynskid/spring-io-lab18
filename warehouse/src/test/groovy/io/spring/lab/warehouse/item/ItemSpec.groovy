package io.spring.lab.warehouse.item

import spock.lang.Specification
import spock.lang.Unroll

import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.TEN

class ItemSpec extends Specification {

    def "Should not update item with null changes"() {
        given:
            Item item = new Item()
        when:
            item.update(null)
        then:
            def ex = thrown(NullPointerException)
            ex.message == 'Changes cannot be null'
    }

    def "Should update item"() {
        given:
            Item item = new Item(1L, 'item', 5, ONE)
        when:
            item.update(ItemUpdate.of('test item', TEN).withId(1L))
        then:
            item.id == 1L
            item.name == 'test item'
            item.count == 5
            item.price == TEN
    }

    @Unroll
    def "Should not update invalid stock"() {
        given:
            Item item = new Item(1L, 'item', stock, ONE)
        when:
            item.updateStock(ItemStockUpdate.of(order).withId(1L))
        then:
            def ex = thrown(result)
            ex.message == message
        where:
            stock | order | result                   | message
            5     |  0    | IllegalArgumentException | 'CountDiff cannot be zero'
            5     | -7    | OutOfStock               | 'Item item has only 5 out of -7 requested'
    }

    @Unroll
    def "Should check out when available"() {
        given:
            Item item = new Item(1L, 'item', stock, ONE)
        when:
            item.updateStock(ItemStockUpdate.of(order).withId(1L))
        then:
            item.count == result
        where:
            stock | order | result
            8     |  1    |  9
            7     |  2    |  9
            5     |  4    |  9
            6     |  6    | 12
            8     | -5    |  3
            4     | -4    |  0
    }
}
