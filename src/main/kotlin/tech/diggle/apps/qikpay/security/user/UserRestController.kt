package tech.diggle.apps.qikpay.security.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import tech.diggle.apps.qikpay.security.jwt.JwtTokenUtil
import tech.diggle.apps.qikpay.security.jwt.JwtUser

import javax.servlet.http.HttpServletRequest

@RestController
class UserRestController(@Autowired val repository: UserRepository) {

    @Value("\${jwt.header}")
    private val tokenHeader: String? = null

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil? = null

    @Autowired
    @Qualifier("jwtUserDetailService")
    private val userDetailsService: UserDetailsService? = null

    @Autowired
    private val userDetailService: UserDetailServiceImpl? = null

    @RequestMapping("user", method = [(RequestMethod.GET)])
    fun getAuthenticatedUser(request: HttpServletRequest): JwtUser {
        val token = request.getHeader(tokenHeader).substring(7)
        val username = jwtTokenUtil!!.getUsernameFromToken(token)
        return userDetailsService!!.loadUserByUsername(username) as JwtUser
    }

    @GetMapping("user/{userName}")
    fun getUser(@PathVariable userName: String): JwtUser {
        return userDetailsService!!.loadUserByUsername(userName) as JwtUser
    }

    @PostMapping("appUser")
    @PreAuthorize("hasRole('ADMIN')")
    fun addUser(@RequestBody appUser: AppUser): JwtUser {
        val usr = userDetailService!!.create(appUser)
        return userDetailsService!!.loadUserByUsername(usr.username) as JwtUser
    }

    @GetMapping("appUsers")
    @PreAuthorize("hasRole('ADMIN')")
    fun listUsers(): List<AppUser> {
        return repository.findAll()
    }


}
