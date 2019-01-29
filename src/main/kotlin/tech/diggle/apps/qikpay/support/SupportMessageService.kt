package tech.diggle.apps.qikpay.support

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import tech.diggle.apps.qikpay.security.user.AppUser

interface SupportMessageService {
    fun getSupportIssue(id: Long): SupportIssue?
    fun getSupportIssue(ref: String): SupportIssue?
    fun getSupportMessages(issueId: Long, page: PageRequest): Page<SupportMessage>
    fun createSupportIssue(issue: SupportIssue): SupportIssue
    fun createSupportIssue(issue: SupportIssueForm, user: AppUser): SupportIssue
    fun addSupportMessage(form: SupportMessageForm): SupportMessage
    fun getIssues(page: PageRequest): Page<SupportIssue>
}