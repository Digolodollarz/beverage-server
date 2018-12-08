package tech.diggle.apps.qikpay.chat

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import tech.diggle.apps.qikpay.security.user.AppUser

interface ChatRepository : PagingAndSortingRepository<Chat, Long> {
    fun findByUsersContains(user: AppUser): List<Chat>
    fun findByUsersContainsAndUsersContainsAndIsGroupIsFalse(user: AppUser, user1: AppUser): Chat?
}

interface ChatMessageRepository : PagingAndSortingRepository<ChatMessage, Long> {
    fun findByChatId(id: Long, page: Pageable): Page<ChatMessage>
}

interface ChatRoomRepository : PagingAndSortingRepository<ChatRoom, Long>