package tech.diggle.apps.qikpay.security.user


import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.PropertyAccessor
import tech.diggle.apps.qikpay.security.authority.Authority
import java.util.Date
import javax.persistence.*

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "USER")
class AppUser {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    var id: Long = 0

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    var username: String? = null

//    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    var password: String? = null

    @Column(name = "FIRSTNAME", length = 50)
    @NotNull
    @Size(min = 2, max = 50)
    var firstname: String? = null

    @Column(name = "LASTNAME", length = 50)
    @NotNull
    @Size(min = 2, max = 50)
    var lastname: String? = null

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    var email: String? = null

    @JsonIgnoreProperties
    @Column(name = "ENABLED")
    @NotNull
    var enabled: Boolean? = null

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    var lastPasswordResetDate: Date? = null

    @JsonIgnoreProperties("appUsers")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_AUTHORITY", joinColumns = arrayOf(JoinColumn(name = "USER_ID", referencedColumnName = "ID")), inverseJoinColumns = arrayOf(JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")))
    var authorities: List<Authority>? = null
}