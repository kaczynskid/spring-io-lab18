package io.spring.lab.marketing.special.calculate

import io.spring.lab.math.MathProperties
import spock.lang.Specification
import spock.lang.Unroll

import static io.spring.lab.marketing.special.calculate.SpecialCalculator.regularPrice

class RegularPriceSpec extends Specification {

    MathProperties math = new MathProperties()

    @Unroll("regular price of #unitCount items should be #totalPrice")
    def "calculates regular price"() {
        when:
            SpecialCalculation calculation = regularPrice.calculateFor(unitCount, 12.0, math)
        then:
            calculation.specialId == null
            calculation.totalPrice == totalPrice
        where:
            unitCount | totalPrice
            2         | 24.0
            3         | 36.0
            5         | 60.0
            7         | 84.0
    }
}
