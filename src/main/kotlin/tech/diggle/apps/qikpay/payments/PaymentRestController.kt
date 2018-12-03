package tech.diggle.apps.qikpay.payments

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("payments")
class PaymentRestController(@Autowired val service: PaymentService) {
    @RequestMapping
    fun get() = service.getAll()

    @RequestMapping(":id")
    fun getPayment(@PathVariable id: Long): AppPayment {
        return service.getPayment(id) ?: throw Exception()
    }

    @PostMapping
    fun makePayment(@RequestBody paymentRequest: PaymentRequest): AppPayment {
        return service.addPayment(paymentRequest)
    }

    @PostMapping("confirm")
    fun confirmPayment(@RequestBody reference: String) = service.confirmPayment(reference)
}