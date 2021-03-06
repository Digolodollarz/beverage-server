package tech.diggle.apps.qikpay.support

import org.omg.CosNaming.NamingContextPackage.NotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.payments.InvalidArgumentStateException
import tech.diggle.apps.qikpay.payments.NullArgumentException
import tech.diggle.apps.qikpay.security.user.AppUser
import tech.diggle.apps.qikpay.security.user.UserRepository
import java.util.Date
import java.time.LocalDateTime

@Service
class SupportMessageServiceImpl(@Autowired val messageRepository: SupportMessageRepository,
                                @Autowired val issueRepository: SupportIssueRepository,
                                @Autowired val userRepository: UserRepository) : SupportMessageService {
    override fun getSupportIssue(id: Long): SupportIssue? {
        return this.issueRepository.findOne(id) ?: throw ChangeSetPersister.NotFoundException()
    }

    //TODO: manage exceptions
    override fun getSupportIssue(ref: String): SupportIssue? {
        return this.issueRepository.findIssueByRef(ref)
    }

    override fun getSupportMessages(issueId: Long, page: PageRequest): Page<SupportMessage> {
        return this.messageRepository.findMessagesByIssueId(issueId, page)
    }

    override fun createSupportIssue(issue: SupportIssue): SupportIssue {
        TODO("Deprecated")
    }

    override fun createSupportIssue(issue: SupportIssueForm, user: AppUser): SupportIssue {
        val newIssue = issueRepository.save(SupportIssue(0, Date(), issue.subject!!, IssueStatus.Open, user))
        newIssue.ref = "IS${newIssue.id}R"
        return issueRepository.save(newIssue)
    }

    override fun addSupportMessage(form: SupportMessageForm): SupportMessage {
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        val username = auth.name
        val user: AppUser = userRepository.findByUsername(username)
                ?: throw IllegalArgumentException("Not Logged In, user not found")
        val issue: SupportIssue = if (form.issue?.id != null) {
            issueRepository.findOne(form.issue?.id)
        } else {
            if (form.issue?.subject == null) throw NullArgumentException("New issue without subject")
            this.createSupportIssue(form.issue!!, user)
        }
        val message = SupportMessage(
                0, Date(), form.message!!, user, issue
        )
        return messageRepository.save(message)
    }

    override fun getIssues(page: PageRequest): Page<SupportIssue> {
        return issueRepository.findAll(page)
    }

    override fun replySupportMessage(form: SupportMessageForm): SupportMessage {
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        val username = auth.name
        val user: AppUser = userRepository.findByUsername(username)
                ?: throw IllegalArgumentException("Not Logged In, user not found")
        val issue: SupportIssue = issueRepository.findOne(form.issue?.id)
                ?: throw InvalidArgumentStateException("Issue not found")
        if (form.message == null) {
            throw NullArgumentException("Message cannot be blank")
        }
        val message = SupportMessage(id = 0,
                dateSent = Date(),
                message = form.message!!,
                issue = issue,
                sender = user)
        return messageRepository.save(message)
    }

    override fun getAllIssues(page: PageRequest): Page<SupportIssue> {
        return issueRepository.findAll(page)
    }
}