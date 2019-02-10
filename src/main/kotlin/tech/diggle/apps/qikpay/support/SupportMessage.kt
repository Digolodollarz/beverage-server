package tech.diggle.apps.qikpay.support

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import tech.diggle.apps.qikpay.DateTo8601
import tech.diggle.apps.qikpay.security.user.AppUser
import java.util.Date
import java.time.*
import javax.persistence.*

class SupportMessageForm {
    var message: String? = null
    var issue: SupportIssueForm? = null
    var sender: AppUser? = null
}


@Entity
data class SupportMessage(
        @Id
        @GeneratedValue
        val id: Long,
        @JsonSerialize(converter = DateTo8601::class)
        val dateSent: Date,
        val message: String,
        @ManyToOne
        val sender: AppUser,
        @ManyToOne
        val issue: SupportIssue) {
    @JsonSerialize(converter = DateTo8601::class)
    var dateRead: Date? = null
}

data class SupportIssueForm(
        @Id
        @GeneratedValue
        val id: Long?,
        @JsonSerialize(converter = DateTo8601::class)
        val dateOpened: Date?,
        val subject: String?,
        val status: IssueStatus?,
        @ManyToOne
        val user: AppUser?
) {
    var ref: String? = null
    @JsonSerialize(converter = DateTo8601::class)
    var lastActivityDate: Date? = null
    @JsonSerialize(converter = DateTo8601::class)
    var dateClosed: Date? = null
    @OneToMany
    var messages: List<SupportMessage> = listOf()
}

@Entity
data class SupportIssue(
        @Id
        @GeneratedValue
        val id: Long,
        @JsonSerialize(converter = DateTo8601::class)
        val dateOpened: Date,
        val subject: String,
        val status: IssueStatus,
        @ManyToOne
        val user: AppUser
) {
    var ref: String? = null
    @JsonSerialize(converter = DateTo8601::class)
    var lastActivityDate: Date? = null
    @JsonSerialize(converter = DateTo8601::class)
    var dateClosed: Date? = null
    @OneToMany
    var messages: List<SupportMessage> = listOf()
}

enum class IssueStatus {
    Open,
    Closed,
    WaitingForClient,
    TimedOut
}