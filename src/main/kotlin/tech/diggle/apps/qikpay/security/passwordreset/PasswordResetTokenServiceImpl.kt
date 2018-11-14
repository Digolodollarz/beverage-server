package tech.diggle.apps.qikpay.security.passwordreset

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.security.user.User
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@Service
class PasswordResetTokenServiceImpl(@Autowired val repository: PasswordTokenRepository)
    : PasswordRestTokenService {
    override fun createToken(user: User): PasswordResetToken {
        val pin = "${ThreadLocalRandom.current().nextInt(0, 10)}" +
                "${ThreadLocalRandom.current().nextInt(0, 10)}" +
                "${ThreadLocalRandom.current().nextInt(0, 10)} - " +
                "${ThreadLocalRandom.current().nextInt(0, 10)}" +
                "${ThreadLocalRandom.current().nextInt(0, 10)}" +
                "${ThreadLocalRandom.current().nextInt(0, 10)}"
        val token = repository.findByUser(user) ?: PasswordResetToken(user)
        token.updateToken(pin)
        return repository.save(token)
    }

    override fun validateToken(token: PasswordResetToken, request: PasswordResetRequest): Boolean {
        val now = Calendar.getInstance()
        return token.token == request.token && now.time.time - token.expiryDate.time < 0
    }

    override fun findByRequest(request: PasswordResetRequest): PasswordResetToken {
        val token = repository.findByUser(User())
        return token!!
    }

    override fun findByUser(user: User) = repository.findByUser(user)

}