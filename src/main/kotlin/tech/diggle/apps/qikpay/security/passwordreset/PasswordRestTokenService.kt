package tech.diggle.apps.qikpay.security.passwordreset

import tech.diggle.apps.qikpay.security.user.AppUser


interface PasswordRestTokenService {
    fun createToken(appUser: AppUser): PasswordResetToken
    fun validateToken(token: PasswordResetToken, request: PasswordResetRequest): Boolean
    fun findByRequest(request: PasswordResetRequest): PasswordResetToken?
    fun findByUser(appUser: AppUser): PasswordResetToken?
}