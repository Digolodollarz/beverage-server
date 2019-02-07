package tech.diggle.apps.qikpay.subscriptions

import tech.diggle.apps.qikpay.payments.AppPayment
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Subscription{
    @Id
    @GeneratedValue
    var id: Long = 0

    var title: String = ""
    var startDate: Date? = null
    var endDate: Date? = null

    @OneToOne
    var payment: AppPayment? = null
}