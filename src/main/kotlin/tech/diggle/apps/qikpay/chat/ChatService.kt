package tech.diggle.apps.qikpay.chat

import org.springframework.data.domain.Page
import tech.diggle.apps.qikpay.security.user.AppUser

interface ChatService {
    fun postMessage(messageForm: ChatMessageForm): ChatMessage
    fun getChats(): List<Chat>
    fun getChat(chatId: Long, page: Int = 0): Chat?
    fun getChat(user1: AppUser, user2: AppUser): Chat?
}