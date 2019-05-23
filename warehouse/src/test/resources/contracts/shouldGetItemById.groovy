import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        name('should get coffee mug by id')
        method('GET')
        url('/items/11')
        headers {
            header('Accept', 'application/json;charset=UTF-8')
        }
    }

    response {
        status(200)
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                name: 'Coffee Mug',
                price: 123.50,
                instanceId: '8080'
        ])
    }
}
