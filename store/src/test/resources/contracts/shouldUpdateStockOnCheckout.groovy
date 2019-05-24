package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    input {
        name('update stock on checkout')
        label('update_stock')
        triggeredBy('basketCheckout()')
    }
    outputMessage {
        sentTo('item-stock-update')
        headers {
            messagingContentType(applicationJson())
        }
        body([
                id: 11,
                countDiff: -2
        ])
    }
}
