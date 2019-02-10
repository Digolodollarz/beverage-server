package tech.diggle.apps.qikpay.payments

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface PaymentService {
    fun getPayment(id: Long): AppPayment?
    fun addPayment(request: PaymentRequest): AppPayment
    fun checkPayment(reference: String): PaymentStatus
    fun confirmPayment(reference: String): Any
    fun getAll(): List<AppPayment>
    fun getAllAdmin(page: PageRequest): Page<AppPayment>
}