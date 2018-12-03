package tech.diggle.apps.qikpay.security.authority


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import tech.diggle.apps.qikpay.security.user.AppUser
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "AUTHORITY")
class Authority {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
    var id: Long? = null

    @Column(name = "NAME", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    var name: AuthorityName? = null

    @JsonIgnoreProperties("authorities")
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    var appUsers: List<AppUser>? = null
}