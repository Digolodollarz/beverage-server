package tech.diggle.apps.qikpay.beverage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BeverageServiceImpl(@Autowired val repository: BeverageRepository) : BeverageService {
    override fun add(beverage: Beverage): Beverage {
        return repository.save(beverage)
    }

    override fun getFirstIn(): Beverage? {
        return repository.getAllByPouredFalse().firstOrNull()
    }

    override fun markDone(beverage: Beverage) {
        beverage.poured = true
        repository.save(beverage)
    }
}