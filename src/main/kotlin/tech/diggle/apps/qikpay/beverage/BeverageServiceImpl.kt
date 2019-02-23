package tech.diggle.apps.qikpay.beverage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.payments.InvalidArgumentStateException

@Service
class BeverageServiceImpl(@Autowired val repository: BeverageRepository) : BeverageService {
    override fun add(beverage: Beverage): Beverage {
        return repository.save(beverage)
    }

    override fun getFirstIn(): Beverage? {
        return repository.getAllByPouredFalse().firstOrNull()
    }

    override fun markDone(id: Long) {
        val beverage = repository.getOne(id)?:throw InvalidArgumentStateException("Unknown ID")
        beverage.poured = true
        repository.save(beverage)
    }
}