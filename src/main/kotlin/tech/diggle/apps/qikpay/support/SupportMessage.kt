package tech.diggle.apps.qikpay.support

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
        val dateSent: Date,
        val message: String,
        @ManyToOne
        val sender: AppUser,
        @ManyToOne
        val issue: SupportIssue) {
    var dateRead: Date? = null
}

data class SupportIssueForm(
        @Id
        @GeneratedValue
        val id: Long?,
        val dateOpened: Date?,
        val subject: String?,
        val status: IssueStatus?,
        @ManyToOne
        val user: AppUser?
) {
    var ref: String? = null
    var lastActivityDate: Date? = null
    var dateClosed: Date? = null
    @OneToMany
    var messages: List<SupportMessage> = listOf()
}

@Entity
data class SupportIssue(
        @Id
        @GeneratedValue
        val id: Long,
        val dateOpened: Date,
        val subject: String,
        val status: IssueStatus,
        @ManyToOne
        val user: AppUser
) {
    var ref: String? = null
    var lastActivityDate: Date? = null
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