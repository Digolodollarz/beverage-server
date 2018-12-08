package tech.diggle.apps.qikpay.chat

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile
import tech.diggle.apps.qikpay.security.user.AppUser
import java.sql.Time
import java.util.*
import javax.persistence.*

@Entity
class Chat {
    @Id
    @GeneratedValue
    var id: Long = 0

    @ManyToMany
    var users: List<AppUser> = listOf()

//    @OneToMany
    @Transient
    @JsonInclude
    var messages: Page<ChatMessage>? = null

    @OneToOne
    var lastMessage: ChatMessage? = null

    var isGroup: Boolean = false
}

@Entity
class ChatMessage() {
    constructor(text: String,
                sentTime: Time) : this() {
        this.text = text
        this.sentTime = sentTime
    }

    @Id
    @GeneratedValue
    var id: Long = 0

//    @ManyToOne(targetEntity = Chat::class)
    var chatId: Long? = null

//    @ManyToOne
    var senderId: Long? = null

    @Column(nullable = false)
    var text: String? = null

    @Column(nullable = false)
    var sentTime: Date? = null

    var deliveryTime: Date? = null

    var readTime: Date? = null

}

@Entity
class ChatRoom {
    @Id
    @GeneratedValue
    var id: Long = 0

    @ManyToMany
    var users: List<AppUser> = listOf()
}

class ChatMessageForm {
    val text: String? = null
    val attachments: List<MultipartFile>? = null
    val recipientChatId: Long? = null
    val recipientUsername: String? = null
}

class ChatForm {
    val groupName: String? = null
    val groupDescription: String? = null
    val groupIcon: String? = null
}
