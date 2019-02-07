package tech.diggle.apps.qikpay.payments

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.util.StdConverter
import tech.diggle.apps.qikpay.security.user.AppUser
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
        @JsonSerialize(converter = DateTo8601::class)
        val createdAt: Date,
        val amount: Double) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0
    @JsonSerialize(converter = DateTo8601::class)
    var updatedAt: Date? = null
    var paid: Boolean = false
    @JsonSerialize(converter = DateTo8601::class)
    var datePaid: Date? = null
}

internal class DateTo8601 : StdConverter<Date, String>() {
    override fun convert(date: Date): String {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE_TIME)
    }
}