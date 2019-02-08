package tech.diggle.apps.qikpay.subscriptions

import tech.diggle.apps.qikpay.payments.AppPayment
import tech.diggle.apps.qikpay.security.user.AppUser
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class Subscription(
        @Id
        @GeneratedValue
        val id: Long,
        var title: String,
        var startDate: Date,
        var endDate: Date,
        @OneToOne
        var payment: AppPayment,
        val user: AppUser) {

    var active: Boolean = false
}