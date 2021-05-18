package vn.com.mta.science.module.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.mta.science.module.model.Affiliation;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User extends Person {

    @Column(name = "username", updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "detail_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "degree")
    private String degree;

    @Column(name = "title")
    private String title;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "hidden")
    private Boolean hidden;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "affiliationid", insertable=false, updatable=false)
    private Affiliation affiliation;

    @Column(name = "affiliationid", nullable = false)
    private Long affiliationId;

    @Column(name = "authorid")
    private Long authorId;

    @Column(name = "groupid")
    private Long groupId;

    public User(Long id) {setId(id);}
}
