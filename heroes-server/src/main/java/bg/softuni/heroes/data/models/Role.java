package bg.softuni.heroes.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseLongEntity {

    @Column(name = "authority", nullable = false)
    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
