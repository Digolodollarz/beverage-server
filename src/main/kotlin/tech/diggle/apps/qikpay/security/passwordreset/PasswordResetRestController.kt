package tech.diggle.apps.qikpay.security.passwordreset

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.diggle.apps.qikpay.security.user.User
import tech.diggle.apps.qikpay.security.user.UserDetailServiceImpl
import java.security.Principal
import java.util.*


@RestController
@RequestMapping("auth/reset")
class PasswordResetRestController(@Autowired val userDetails: UserDetailServiceImpl,
                                  @Autowired val tokenService: PasswordRestTokenService) {
    /**
     * Method is used to request for password reset token
     * The parameter passed is either username or email. If a user is not found an exception is thrown.
     * No one knows the exception.
     * TODO: Throw 4xx error instead!
     */

    @PostMapping("request")
    fun getToken(@RequestBody request: PasswordResetRequest): Date {
        val user: User = userDetails.findByUsernameOrEmail(request.username!!)
                ?: throw IllegalArgumentException("Username not found. Please try again")
        val token = tokenService.createToken(user)
        return token.expiryDate
    }

    @PostMapping("update")
    fun changePassword(@RequestBody request: PasswordResetRequest): String {
        if (request.username == null) throw KotlinNullPointerException("Username")
        if (request.token == null) throw KotlinNullPointerException("Token")
        if (request.newPassword == null) throw KotlinNullPointerException("New Password")
        if (request.newPassword.length < 6) throw IllegalArgumentException("Password too short")
        val user = userDetails.findByUsernameOrEmail(request.username) ?: throw Exception("User not found")
        val token = tokenService.findByUser(user) ?: throw Exception("Token Expired")
        if (tokenService.validateToken(token, request)) {
            userDetails.update(userDetails.updatePassword(user, request.newPassword))
            return "done."
        }
        return "Expired or Invalid Token"
    }

    /**
     * Change password the normal way
     * Parameters: Current password, username, new password
     * Requires authentication
     * TODO: Do it in another controller
     */
    @PostMapping("change")
    fun updatePassword(@AuthenticationPrincipal user: Principal, @RequestBody request: PasswordResetRequest){
        if(user == null) throw NullPointerException()

    }

}