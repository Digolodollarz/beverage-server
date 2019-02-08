package tech.diggle.apps.qikpay.subscriptions

import org.springframework.data.domain.Page

interface SubscriptionService {
    fun getAll(page: Int = 0, count: Int = 20): Page<Subscription>
    fun addNew(form: SubscriptionForm): Subscription
}
