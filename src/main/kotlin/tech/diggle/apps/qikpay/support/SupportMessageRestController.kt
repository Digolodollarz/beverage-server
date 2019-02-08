package tech.diggle.apps.qikpay.support

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("support")
class SupportMessageRestController(@Autowired val service: SupportMessageService) {
    @GetMapping
    fun getAll(
            @RequestParam("page") page: Optional<Int>,
            @RequestParam("size") size: Optional<Int>
    ): Page<SupportIssue> {
        val pageRequest = PageRequest(page.orElse(0), size.orElse(20))
        return service.getIssues(pageRequest)
    }

    @PostMapping
    fun sendMessage(@RequestBody messageForm: SupportMessageForm): SupportMessage {
        return service.addSupportMessage(messageForm)
    }

    @GetMapping("{id}")
    fun getIssue(@PathVariable id: Long) = service.getSupportIssue(id)

    @PostMapping("admin")
    fun replyMessage(@RequestBody messageForm: SupportMessageForm): SupportMessage {
        return service.addSupportMessage(messageForm)
    }

    @GetMapping("admin/{id}")
    fun getIssueAdmin(@PathVariable id: Long) = service.getSupportIssue(id)


    @GetMapping
    fun getAllAdmin(
            @RequestParam("page") page: Optional<Int>,
            @RequestParam("size") size: Optional<Int>
    ): Page<SupportIssue> {
        val pageRequest = PageRequest(page.orElse(0), size.orElse(20))
        return service.getAllIssues(pageRequest)
    }
}