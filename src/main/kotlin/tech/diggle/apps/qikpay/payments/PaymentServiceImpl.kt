package tech.diggle.apps.qikpay.payments

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.security.user.UserRepository
import webdev.payments.Paynow
import java.util.*
import kotlin.collections.HashMap

@Service
class PaymentServiceImpl(val repository: PaymentRepository,
                         val userRepository: UserRepository) : PaymentService {
    override fun getAll(): List<AppPayment> {
        return repository.findAll()
    }

    override fun checkPayment(reference: String): PaymentStatus {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPayment(id: Long): AppPayment? {
        return repository.getOne(id)
    }

    override fun addPayment(request: PaymentRequest): AppPayment {
        if (request.items == null) throw IllegalArgumentException("No Items passed")
        if (request.reference.isNullOrEmpty()) print("TODO: Here something what can")
        if (repository.findByReference(request.reference!!) != null)
            throw IllegalStateException("Duplicate transaction")
        val paynow = Paynow("6561",
                "c16b43fc-fd5e-4a3f-863c-8a8fba24ff2d")
        if (request.method == PaymentMethod.ECOCASH) {
            if (request.phone.isNullOrEmpty())
                throw IllegalArgumentException("Please provide phone")
//            Fetch the currently logged in user's email address
            val auth: Authentication = SecurityContextHolder.getContext().authentication
            val username = auth.name
            val user = userRepository.findByUsername(username)
                    ?: throw IllegalArgumentException("Not Logged In, user not found")
            val payment = paynow.createPayment(request.reference,
                    request.email?.toLowerCase() ?: user.email?.toLowerCase())
            request.items?.forEach { payment.add(it.key, it.value) }
            val paymentResponse = paynow.sendMobile(payment, request.phone,
                    PaymentMethod.ECOCASH.name)
            return if (paymentResponse.success()) {
                val appPayment = AppPayment(
                        createdAt = Date(),
                        link = paymentResponse.redirectLink(),
                        pollUrl = paymentResponse.pollUrl(),
                        reference = payment.reference,
                        instructions = paymentResponse.instructions,
                        user = user,
                        amount = payment.total.toDouble())
                repository.save(appPayment)
            } else throw InvalidArgumentStateException(paymentResponse.errors())
        } else {
            val payment = paynow.createPayment(request.reference)
            request.items?.forEach { payment.add(it.key, it.value) }
            val paymentResponse = paynow.send(payment)
            return if (paymentResponse.success()) {
                val auth: Authentication = SecurityContextHolder.getContext().authentication
                val username = auth.name
                val user = userRepository.findByUsername(username)
                        ?: throw IllegalArgumentException("Not Logged In, user not found")
                val appPayment = AppPayment(
                        createdAt = Date(),
                        link = paymentResponse.redirectLink(),
                        pollUrl = paymentResponse.pollUrl(),
                        reference = payment.reference,
                        user = user,
                        amount = payment.total.toDouble())
                repository.save(appPayment)
            } else throw IllegalStateException("Failed to initiate transaction")
        }
    }

    /**
     * Check the status of a payment
     * @param reference: the reference for the payment being checked
     * @return @tech.diggle.apps.qikpay.payments.PaymentStatus
     *
     * Check the payment status, if paid return;
     * Poll the Paynow server, if payment pending return unpaid;
     * If paid update payment status to paid and return
     */
    override fun confirmPayment(reference: String): Map<String, PaymentStatus> {
        val payment = repository.findByReference(reference)
                ?: throw IllegalArgumentException("Unknown payment reference - $reference")
        if (payment.paid) return mapOf("status" to PaymentStatus.PAID)
        val paynow = Paynow("6561", "c16b43fc-fd5e-4a3f-863c-8a8fba24ff2d")
        val status = paynow.pollTransaction(payment.pollUrl)
        val stat = paynow.processStatusUpdate(status.data as HashMap<String, String>)

        return if (status.paid()) {
            if (status.amount.toDouble() != payment.amount) throw IllegalArgumentException("Fraudulent activity detected")
            payment.datePaid = Date()
            payment.paid = true
            payment.updatedAt = Date()
            repository.save(payment)
            mapOf("status" to PaymentStatus.PAID)
        } else if (status.data["status"] == PaymentStatus.Created.name ||
                status.data["status"] == PaymentStatus.Sent.name)
            mapOf("status" to PaymentStatus.PENDING)
        else if (status.data["status"] == "Awaiting Delivery") {
            if (status.amount.toDouble() != payment.amount) throw IllegalArgumentException("Fraudulent activity detected")
            payment.datePaid = Date()
            payment.paid = true
            payment.updatedAt = Date()
            repository.save(payment)
            mapOf("status" to PaymentStatus.PAID)
        } else mapOf("status" to PaymentStatus.Cancelled)
    }
}