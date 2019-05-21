package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        name('calculate regular price')
        method('POST')
        url('/specials/11/calculate')
        body([
                unitPrice: 123.5,
                unitCount: 2
        ])
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }
    response {
        status 200
        body([
                totalPrice: 247.0
        ])
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }
}
