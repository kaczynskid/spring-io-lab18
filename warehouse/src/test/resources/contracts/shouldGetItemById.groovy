import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        name('should get coffee mug by id')
        method('GET')
        url('/items/11')
        headers {
            header('Accept', 'application/json')
        }
    }

    response {
        status(200)
        headers {
            header('Content-Type', 'application/json')
        }
        body([
                name: 'Coffee Mug',
                price: 123.50
        ])
    }
}
