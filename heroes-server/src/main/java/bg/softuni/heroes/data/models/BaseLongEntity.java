package bg.softuni.heroes.data.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Setter(AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseLongEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @Access(AccessType.PROPERTY)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseLongEntity)) return false;
        BaseLongEntity that = (BaseLongEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}