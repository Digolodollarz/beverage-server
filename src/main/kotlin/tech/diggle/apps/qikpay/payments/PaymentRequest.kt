package tech.diggle.apps.qikpay.payments

import tech.diggle.apps.qikpay.security.user.AppUser

class PaymentRequest {
    var user: AppUser? = null
    var items: Map<String, Double>? = null
    var reference: String? = null
    var method: PaymentMethod? = null
    var methodDetail: String? = null
    var email: String? = null
    var phone: String? = null
}