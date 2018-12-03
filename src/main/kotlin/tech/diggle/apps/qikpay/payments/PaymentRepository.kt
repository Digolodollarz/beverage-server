package tech.diggle.apps.qikpay.payments

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<AppPayment, Long>{
    fun findByReference(reference: String): AppPayment?
}