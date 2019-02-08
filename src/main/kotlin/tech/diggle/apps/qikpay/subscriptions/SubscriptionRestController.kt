package tech.diggle.apps.qikpay.subscriptions

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscriptions")
class SubscriptionRestController(@Autowired val service: SubscriptionService){
    fun get() = service.getAll()

    fun post(@RequestBody subscription: SubscriptionForm) = service.addNew(subscription)
}