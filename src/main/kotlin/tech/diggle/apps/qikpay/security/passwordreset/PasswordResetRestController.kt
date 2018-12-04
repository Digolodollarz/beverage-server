package tech.diggle.apps.qikpay.security.passwordreset

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.diggle.apps.qikpay.payments.InvalidArgumentStateException
import tech.diggle.apps.qikpay.payments.NullArgumentException
import tech.diggle.apps.qikpay.security.user.AppUser
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
    fun getToken(@RequestBody request: PasswordResetRequest): Map<String, Date> {
        val appUser: AppUser = userDetails.findByUsernameOrEmail(request.username!!)
                ?: throw IllegalArgumentException("Username not found. Please try again")
        val token = tokenService.createToken(appUser)

        return hashMapOf("expires" to token.expiryDate)
    }

    @PostMapping("update")
    fun changePassword(@RequestBody request: PasswordResetRequest): Map<String, Any> {
        if (request.username == null) throw NullArgumentException("Username is empty")
        if (request.token == null) throw NullArgumentException("Token is empty")
        if (request.newPassword == null) throw NullArgumentException("New Password empty")
        if (request.newPassword.length < 6) throw InvalidArgumentStateException("Password too short")
        val user = userDetails.findByUsernameOrEmail(request.username) ?: throw Exception("AppUser not found")
        val token = tokenService.findByUser(user) ?: throw InvalidArgumentStateException("Invalid token")
        if (tokenService.validateToken(token, request)) {
            userDetails.update(userDetails.updatePassword(user, request.newPassword))
            return hashMapOf("message" to "done")
        }
        throw InvalidArgumentStateException("Expired or Invalid Token")
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