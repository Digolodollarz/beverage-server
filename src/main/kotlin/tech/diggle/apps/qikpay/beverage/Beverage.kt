package tech.diggle.apps.qikpay.beverage

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Beverage {
    @GeneratedValue
    @Id
    val id: Long = 0
    var amount: Int = 0
    var poured: Boolean = false
}

@Entity
class UnpouredBeverage{
    @GeneratedValue
    @Id
    val id: Long = 0
    val pin: Int = 0
    val amount: Int = 0
}
