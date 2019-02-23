package tech.diggle.apps.qikpay.beverage

interface BeverageService {
    fun add(beverage: Beverage): Beverage
    fun getFirstIn(): Beverage?
    fun markDone(id: Long)
}