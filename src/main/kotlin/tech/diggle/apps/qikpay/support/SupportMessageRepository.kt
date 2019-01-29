package tech.diggle.apps.qikpay.support

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface SupportMessageRepository: PagingAndSortingRepository<SupportMessage, Long>{
    fun findMessagesByIssueId(issueId: Long, pageable: Pageable): Page<SupportMessage>
}

interface SupportIssueRepository: PagingAndSortingRepository<SupportIssue, Long>{
    fun findIssueByRef(ref: String): SupportIssue?
}
