package tech.diggle.apps.qikpay.security.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import tech.diggle.apps.qikpay.security.authority.AuthorityName
import tech.diggle.apps.qikpay.security.authority.AuthorityRepository
import java.util.*

@Service
class UserDetailServiceImpl(val userRepository: UserRepository,
                            val authorityRepository: AuthorityRepository) {
    val bCrypt: BCryptPasswordEncoder = BCryptPasswordEncoder()
    fun create(appUser: AppUser): AppUser {
        if (appUser.username == null) throw NullPointerException("Username cannot be null")
        if (appUser.password == null) throw NullPointerException("Password yako cannot be nothing")
        appUser.password = bCrypt.encode(appUser.password)
        if (appUser.firstname == null) throw NullPointerException("First Name cannot be empty")
        if (appUser.lastname == null) throw NullPointerException("Last name cannot be empty")
        if (appUser.email == null) throw NullPointerException("Email address cannot be empty")
        appUser.enabled = true
        if (appUser.lastPasswordResetDate == null) appUser.lastPasswordResetDate = Date(1509494400)
        val roleUser = authorityRepository.findByName(AuthorityName.ROLE_USER)
        val authorities = authorityRepository.findAll()
        appUser.authorities = listOf(roleUser)
        if (appUser.authorities == null || appUser.authorities!!.isEmpty()) throw IllegalArgumentException("Failed to set appUser. Retry")
        if (userRepository.findByUsername(appUser.username!!) != null) throw IllegalArgumentException("Username taken")
        return userRepository.save(appUser)
    }

    fun update(appUser: AppUser): AppUser {
        val usr = userRepository.findByUsername(appUser.username!!) ?: throw IllegalArgumentException("Invalid appUser")

        return userRepository.save(usr)
    }

    fun findByUsernameOrEmail(userName: String): AppUser? {
        return userRepository.findByUsername(userName) ?: userRepository.findByEmail(userName)
    }

    fun updatePassword(appUser: AppUser, password: String): AppUser {
        appUser.lastPasswordResetDate = Date()
        appUser.password = bCrypt.encode(password)
        return appUser
    }
}