package tech.diggle.apps.qikpay.chat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.payments.InvalidArgumentStateException
import tech.diggle.apps.qikpay.security.user.AppUser
import tech.diggle.apps.qikpay.security.user.UserRepository
import java.util.Date
import java.sql.Time

@Service
class ChatServiceImp(@Autowired val chatRepository: ChatRepository,
                     @Autowired val chatMessageRepository: ChatMessageRepository,
                     @Autowired val userRepository: UserRepository) : ChatService {
    override fun postMessage(messageForm: ChatMessageForm): ChatMessage {
        val user = getUser()
        var chatMessage = ChatMessage()
        val chat: Chat = if (messageForm.recipientChatId != null) {
            chatRepository.findOne(messageForm.recipientChatId)
                    ?: throw InvalidArgumentStateException("Invalid chat id")
        } else {
            val recipientUser = userRepository.findByUsername(messageForm.recipientUsername!!)
                    ?: throw InvalidArgumentStateException("Unknown recipient")
            var _chat = chatRepository.findByUsersContainsAndUsersContainsAndIsGroupIsFalse(user, recipientUser)
            if (_chat == null) {
                _chat = Chat()
                _chat.users = listOf(user, recipientUser)
                _chat = chatRepository.save(_chat)
            }
//            _chat?.users?.size
            _chat!!
        }
//        chat.users.size
        chatMessage.chatId = chat.id
        chatMessage.senderId = user.id
        chatMessage.text = messageForm.text
        chatMessage.sentTime = Date()
        chatMessage = chatMessageRepository.save(chatMessage)
        chat.lastMessage = chatMessage
        chatRepository.save(chat)
        return chatMessage
    }

    override fun getChats(): List<Chat> {
        val user = getUser()
        return chatRepository.findByUsersContains(user)
    }

    override fun getChat(chatId: Long, page: Int): Chat? {
        val user = getUser()
        val chat = chatRepository.findOne(chatId)
        chat.messages = chatMessageRepository.findByChatId(chatId, PageRequest(page, 20))
        return chat
    }

    override fun getChat(user1: AppUser, user2: AppUser): Chat? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getUser(): AppUser {
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        val username = auth.name
        return userRepository.findByUsername(username)
                ?: throw IllegalArgumentException("Not Logged In, user not found")
    }
}