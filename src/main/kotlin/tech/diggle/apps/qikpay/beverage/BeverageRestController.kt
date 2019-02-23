package tech.diggle.apps.qikpay.beverage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("beverage")
class BeverageRestController(@Autowired val service: BeverageService) {
    @GetMapping
    fun get() = service.getFirstIn()

    @PostMapping
    fun add(@RequestBody beverage: Beverage) = service.add(beverage)

    @PostMapping("{id}")
    fun poured(@PathVariable id: Long) = service.markDone(id)
}