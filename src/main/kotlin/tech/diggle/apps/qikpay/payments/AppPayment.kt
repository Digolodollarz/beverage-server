package tech.diggle.apps.qikpay.payments

import tech.diggle.apps.qikpay.security.user.AppUser
import java.util.*
import javax.persistence.*

@Entity
data class AppPayment(
        @ManyToOne
        val user: AppUser,
        @Column(unique = true)
        val reference: String,
        val pollUrl: String,
        val link: String,
        val instructions: String = "",
        val createdAt: Date,
        val amount: Double) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0
    var updatedAt: Date? = null
    var paid: Boolean = false
    var datePaid: Date? = null
}