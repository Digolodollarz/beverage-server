package tech.diggle.apps.qikpay.beverage

import org.springframework.data.jpa.repository.JpaRepository

interface BeverageRepository : JpaRepository<Beverage, Long> {
    fun getAllByPouredFalse(): List<Beverage>
}