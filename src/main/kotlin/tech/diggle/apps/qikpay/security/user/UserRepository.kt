package tech.diggle.apps.qikpay.security.user


import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<AppUser, Long> {
    fun findByUsername(username: String): AppUser?
    fun findByEmail(email: String): AppUser?
}