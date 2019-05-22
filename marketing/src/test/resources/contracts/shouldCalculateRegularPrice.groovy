package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        name('calculate regular price')
        method('POST')
        url('/specials/11/calculate')
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                unitPrice: 123.5,
                unitCount: 2
        ])
    }
    response {
        status 200
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                totalPrice: 247.0
        ])
    }
}
