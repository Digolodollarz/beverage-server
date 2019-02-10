package tech.diggle.apps.qikpay.subscriptions

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import tech.diggle.apps.qikpay.DateTo8601
import tech.diggle.apps.qikpay.payments.AppPayment
import java.util.*

class SubscriptionForm {
    var id: Long? = null
    var title: String? = null
    @JsonSerialize(converter = DateTo8601::class)
    var startDate: Date? = null
    @JsonSerialize(converter = DateTo8601::class)
    var endDate: Date? = null
    var payment: AppPayment? = null
}