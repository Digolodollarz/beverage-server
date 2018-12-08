package tech.diggle.apps.qikpay.chat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("chats")
class ChatRestController(@Autowired val service: ChatService) {
//    @GetMapping
//    fun get() = { "Silence is golden" }

    @GetMapping()
    fun getAll() = service.getChats()

    @PostMapping
    fun postMessage(@RequestBody messageForm: ChatMessageForm) = service.postMessage(messageForm)

    @GetMapping("{id}")
    fun getChat(@PathVariable id: Long) = service.getChat(id)
}