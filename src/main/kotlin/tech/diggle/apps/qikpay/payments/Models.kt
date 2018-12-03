package tech.diggle.apps.qikpay.payments

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

enum class PaymentStatus {
    PENDING,
    PAID,
    Created,
    Sent,
    Cancelled,
    Disputed,
    Refunded
}

enum class PaymentMethod {
    ECOCASH,
    ONEWALLET,
    TELECASH,
    VISA
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidArgumentStateException(override val message: String?) : RuntimeException(message)