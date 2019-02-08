package tech.diggle.apps.qikpay.subscriptions

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.payments.InvalidArgumentStateException
import tech.diggle.apps.qikpay.payments.NullArgumentException
import tech.diggle.apps.qikpay.payments.PaymentService
import tech.diggle.apps.qikpay.security.user.UserRepository

@Service
class SubscriptionServiceImpl(@Autowired val repository: SubscriptionRepository,
                              @Autowired val paymentService: PaymentService,
                              @Autowired val userRepository: UserRepository) : SubscriptionService {
    override fun getAll(page: Int, count: Int): Page<Subscription> {
        return repository.findAll(PageRequest(page, count))
    }

    override fun addNew(form: SubscriptionForm): Subscription {
        if (form.payment == null) {
            throw NullArgumentException("Payment is null")
        }
        if (form.startDate == null || form.endDate == null) {
            throw NullArgumentException("Subscription dates are not defined")
        }
        if (form.title == null) {
            throw NullArgumentException("Subscription title has not been offered")
        }
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        val username = auth.name
        val user = userRepository.findByUsername(username)
                ?: throw IllegalArgumentException("Not Logged In, user not found")
//        Here we check to see if the payment has already been to some other subscription just for fun. And to slow things down
        val payment = paymentService.getPayment(form.payment!!.id)
                ?: throw InvalidArgumentStateException("You need to pay")

        val subscription = Subscription(
                id = 0,
                title = form.title!!,
                startDate = form.startDate!!,
                endDate = form.endDate!!,
                payment = form.payment!!,
                user = user
        )

        return repository.save(subscription)
    }

}
