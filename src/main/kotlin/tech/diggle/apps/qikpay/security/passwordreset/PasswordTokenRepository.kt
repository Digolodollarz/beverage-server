package tech.diggle.apps.qikpay.security.passwordreset

import org.springframework.data.jpa.repository.JpaRepository
import tech.diggle.apps.qikpay.security.user.User

interface PasswordTokenRepository: JpaRepository<PasswordResetToken, Long>{
    fun findByUser(user: User): PasswordResetToken?
}