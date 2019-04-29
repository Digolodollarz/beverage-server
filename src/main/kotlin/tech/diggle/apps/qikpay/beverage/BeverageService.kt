package tech.diggle.apps.qikpay.beverage

interface BeverageService {
    fun add(beverage: Beverage): Beverage
    fun store(beverage: UnpouredBeverage): UnpouredBeverage
    fun getFirstIn(): Beverage?
    fun markDone(id: Long)
    fun restore(beverage: UnpouredBeverage): Beverage?
}