package tech.diggle.apps.qikpay.beverage

import javassist.tools.web.BadHttpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.payments.InvalidArgumentStateException

@Service
class BeverageServiceImpl(@Autowired val repository: BeverageRepository,
                          @Autowired val unpouredBeverageRepository: UnpouredBeverageRepository) : BeverageService {
    override fun store(beverage: UnpouredBeverage): UnpouredBeverage {
        return unpouredBeverageRepository.save(beverage)
    }

    override fun restore(beverage: UnpouredBeverage): Beverage? {
        val newBeverage = unpouredBeverageRepository.findByPin(beverage.pin) ?: throw BadHttpRequest()
        val bev = Beverage()
        bev.poured = false
        bev.amount = newBeverage.amount
        return repository.save(bev)

    }

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