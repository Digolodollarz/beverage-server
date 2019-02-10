package tech.diggle.apps.qikpay.payments

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface PaymentRepository : PagingAndSortingRepository<AppPayment, Long> {
    fun findByReference(reference: String): AppPayment?
    fun findByUserId(userId: Long): List<AppPayment>
}