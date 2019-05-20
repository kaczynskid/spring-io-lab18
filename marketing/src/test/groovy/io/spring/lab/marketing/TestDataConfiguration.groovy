package io.spring.lab.marketing

import io.spring.lab.marketing.special.Special
import io.spring.lab.marketing.special.SpecialRepository

class TestDataConfiguration {

    static void specialsTestData(SpecialRepository specials) {
        specials.save(new Special(null, 1, 3, 70.0))
        specials.save(new Special(null, 2, 2, 15.0))
        specials.save(new Special(null, 3, 4, 60.0))
        specials.save(new Special(null, 4, 2, 40.0))
    }

}
