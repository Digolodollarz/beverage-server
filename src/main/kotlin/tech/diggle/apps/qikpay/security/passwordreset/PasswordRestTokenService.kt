package tech.diggle.apps.qikpay.security.passwordreset

import tech.diggle.apps.qikpay.security.user.User


interface PasswordRestTokenService {
    fun createToken(user: User): PasswordResetToken
    fun validateToken(token: PasswordResetToken, request: PasswordResetRequest): Boolean
    fun findByRequest(request: PasswordResetRequest): PasswordResetToken?
    fun findByUser(user: User): PasswordResetToken?
}