import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        name('should not get missing item by id')
        method('GET')
        url('/items/101')
        headers {
            header('Accept', 'application/json;charset=UTF-8')
        }
    }

    response {
        status(404)
    }
}
