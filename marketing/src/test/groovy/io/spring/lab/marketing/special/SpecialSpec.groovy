package io.spring.lab.marketing.special

import io.spring.lab.marketing.special.calculate.SpecialCalculation
import io.spring.lab.math.MathProperties
import spock.lang.Specification
import spock.lang.Unroll

class SpecialSpec extends Specification {

    MathProperties math = new MathProperties()

    @Unroll("total price of #unitCount items should be #totalPrice")
    def "calculates special price"() {
        given:
            Special special = new Special('1', 0, 5, 50.0)
        when:
            SpecialCalculation calculation = special.calculateFor(unitCount, 12.0, math)
        then:
            calculation.specialId == specialId
            calculation.totalPrice == totalPrice
        where:
            unitCount | specialId | totalPrice
            2         | null      | 24.0
            3         | null      | 36.0
            5         | '1'       | 50.0
            7         | '1'       | 74.0
    }
}
