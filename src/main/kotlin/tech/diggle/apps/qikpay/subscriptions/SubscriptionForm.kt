package tech.diggle.apps.qikpay.subscriptions

import tech.diggle.apps.qikpay.payments.AppPayment
import java.util.*

class SubscriptionForm {
    var id: Long? = null
    var title: String? = null
    var startDate: Date? = null
    var endDate: Date? = null
    var payment: AppPayment? = null
}