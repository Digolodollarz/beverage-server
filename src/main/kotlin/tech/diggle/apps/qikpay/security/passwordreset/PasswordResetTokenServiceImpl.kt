package tech.diggle.apps.qikpay.security.passwordreset

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.security.user.AppUser
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@Service
class PasswordResetTokenServiceImpl(@Autowired val repository: PasswordTokenRepository)
    : PasswordRestTokenService {
    override fun createToken(appUser: AppUser): PasswordResetToken {
        val pin = "${ThreadLocalRandom.current().nextInt(0, 10)}" +
                "${ThreadLocalRandom.current().nextInt(0, 10)}" +
                "${ThreadLocalRandom.current().nextInt(0, 10)} - " +
                "${ThreadLocalRandom.current().nextInt(0, 10)}" +
                "${ThreadLocalRandom.current().nextInt(0, 10)}" +
                "${ThreadLocalRandom.current().nextInt(0, 10)}"
        val token = repository.findByUser(appUser) ?: PasswordResetToken(appUser)
        token.updateToken(pin)
        return repository.save(token)
    }

    override fun validateToken(token: PasswordResetToken, request: PasswordResetRequest): Boolean {
        if (request.token == null ) return false
        val now = Calendar.getInstance()
        val sanitisedToken = request.token.trim()
                .replace(" ", "")
                .replace("-", "")
        val sanitisedDbToken = token.token.trim()
                .replace(" ", "")
                .replace("-", "")
        return sanitisedToken == sanitisedDbToken && now.time.time - token.expiryDate.time < 0
    }

    override fun findByRequest(request: PasswordResetRequest): PasswordResetToken {
        val token = repository.findByUser(AppUser())
        return token!!
    }

    override fun findByUser(appUser: AppUser) = repository.findByUser(appUser)

}