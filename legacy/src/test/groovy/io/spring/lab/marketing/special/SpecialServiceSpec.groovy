package io.spring.lab.marketing.special

import io.spring.lab.marketing.special.calculate.SpecialCalculation
import io.spring.lab.marketing.special.select.BestSpecialSelector
import io.spring.lab.marketing.special.select.SpecialSelector
import io.spring.lab.math.MathProperties
import spock.lang.Specification
import spock.lang.Unroll

import static io.spring.lab.marketing.TestDataConfiguration.specialsTestData

class SpecialServiceSpec extends Specification {

    MathProperties math = new MathProperties()
    SpecialSelector selector = new BestSpecialSelector(math)
    SpecialRepository repository = new StubSpecialRepository()

    SpecialService specials = new SpecialService(repository, selector, math)

    void setup() {
        specialsTestData(repository)
    }

    def "finds all specials"() {
        when:
            def specials = specials.findAll()
        then:
            specials.size() >= 4
    }

    def "creates new special"() {
        when:
            Special special = specials.create(new Special(null, 5L, 5, 24.49))
        then:
            with(specials.findOne(special.id)) {
                itemId == 5L
                batchSize == 5
                batchPrice == 24.49
            }
    }

    def "finds special by item ID"() {
        when:
            List<Special> specials = specials.findByItemId(3L)
        then:
            specials.size() == 1
            with(specials.get(0)) {
                itemId == 3L
                batchSize == 4
                batchPrice == 60.0
            }
    }

    @Unroll("special price of #unitCount items should be #totalPrice")
    def "calculates special price"() {
        when:
            SpecialCalculation calculation = specials.calculateFor(itemId, unitPrice, unitCount)
        then:
            calculation.specialId != null
            calculation.totalPrice == totalPrice
        where:
            itemId | unitPrice | unitCount | totalPrice
            1      | 40.0      | 3         | 70.0
            2      | 10.0      | 2         | 15.0
            3      | 30.0      | 4         | 60.0
            4      | 25.0      | 2         | 40.0
    }
}
