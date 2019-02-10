package tech.diggle.apps.qikpay.subscriptions

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import tech.diggle.apps.qikpay.DateTo8601
import tech.diggle.apps.qikpay.payments.AppPayment
import tech.diggle.apps.qikpay.security.user.AppUser
import java.util.*
import javax.persistence.*

@Entity
data class Subscription(
        @Id
        @GeneratedValue
        val id: Long,
        var title: String,
        @JsonSerialize(converter = DateTo8601::class)
        var startDate: Date,
        @JsonSerialize(converter = DateTo8601::class)
        var endDate: Date,
        @OneToOne
        var payment: AppPayment,
        @ManyToOne
        val user: AppUser) {

    var active: Boolean = false
}