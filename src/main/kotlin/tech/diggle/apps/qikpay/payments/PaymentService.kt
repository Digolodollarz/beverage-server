package tech.diggle.apps.qikpay.payments

interface PaymentService{
    fun getPayment(id:Long): AppPayment?
    fun addPayment(request: PaymentRequest): AppPayment
    fun checkPayment(reference: String): PaymentStatus
    fun confirmPayment(reference: String): PaymentStatus
    @Deprecated("Just don't use this in production") fun getAll(): List<AppPayment>
}