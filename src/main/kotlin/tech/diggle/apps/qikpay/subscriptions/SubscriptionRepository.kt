package tech.diggle.apps.qikpay.subscriptions

import org.springframework.data.repository.PagingAndSortingRepository

interface SubscriptionRepository : PagingAndSortingRepository<Subscription, Long>