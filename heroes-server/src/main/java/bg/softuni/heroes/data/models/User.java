package bg.softuni.heroes.data.models;

import bg.softuni.heroes.data.listeners.AuditListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_username_email", columnNames = {"username", "email"})
        }
)
@EntityListeners(AuditListener.class)
public class User extends BaseUuidEntity implements Auditable {

    private static final long serialVersionUID = 1L;
    private static final int VALID_USERNAME_MAX_LENGTH = 32;
    private static final int VALID_PASSWORD_MAX_LENGTH = 60;
    private static final int VALID_EMAIL_MAX_LENGTH = 64;

    @Column(nullable = false, updatable = false, length = VALID_USERNAME_MAX_LENGTH)
    private String username;

    @Column(nullable = false, length = VALID_PASSWORD_MAX_LENGTH)
    private String password;

    @Column(nullable = false, length = VALID_EMAIL_MAX_LENGTH)
    private String email;

    @OneToOne(mappedBy = "user")
    private Hero hero;

    @Embedded
    private Audit audit;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_users_roles_users_id")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_users_roles_roles_id")
            )
    )
    private Set<Role> roles = new HashSet<>();
}
