package io.spring.lab.marketing.special.select

import io.spring.lab.marketing.special.Special
import io.spring.lab.math.MathProperties
import spock.lang.Specification
import spock.lang.Unroll

class BestSpecialSelectorSpec extends Specification {

    MathProperties math = new MathProperties()
    BestSpecialSelector selector = new BestSpecialSelector(math)

    @Unroll("best price of #unitCount items should be #totalPrice")
    def "selects most profitable special"() {
        given:
            List<Special> specials = [
                    new Special('1', 1L, 2, 15.0),
                    new Special('2', 1L, 4, 29.0),
                    new Special('3', 1L, 5, 40.0)
            ]
        when:
            def calculation = selector
                    .selectSpecial(specials, unitCount, unitPrice)
                    .calculateFor(unitCount, unitPrice, math)
        then:
            calculation.specialId == specialId
            calculation.totalPrice == totalPrice
        where:
            unitPrice | unitCount | specialId | totalPrice
            10.0      | 1         | null      | 10.0
            10.0      | 2         | '1'       | 15.0
            10.0      | 3         | '1'       | 25.0
            10.0      | 4         | '2'       | 29.0
            10.0      | 5         | '2'       | 39.0
             8.0      | 1         | null      |  8.0
             8.0      | 2         | '1'       | 15.0
             8.0      | 3         | '1'       | 23.0
             8.0      | 4         | '2'       | 29.0
             8.0      | 5         | '2'       | 37.0
             7.4      | 1         | null      |  7.4
             7.4      | 2         | null      | 14.8
             7.4      | 3         | null      | 22.2
             7.4      | 4         | '2'       | 29.0
             7.4      | 5         | '2'       | 36.4
    }
}
