package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        name('calculate special price')
        method('POST')
        url('/specials/11/calculate')
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                unitPrice: 123.5,
                unitCount: 5
        ])
    }
    response {
        status 200
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                specialId: 'promo-15-off',
                totalPrice: 524.87
        ])
    }
}
