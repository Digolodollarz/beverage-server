package tech.diggle.apps.qikpay.beverage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("beverage")
class BeverageRestController(@Autowired val service: BeverageService) {
    @GetMapping
    fun get() = service.getFirstIn()

    @PostMapping
    fun poured(@RequestBody beverage: Beverage) = service.markDone(beverage)
}