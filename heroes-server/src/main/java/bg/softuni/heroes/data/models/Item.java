package bg.softuni.heroes.data.models;

import bg.softuni.heroes.data.enums.Slot;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "items",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_items_name", columnNames = {"name"})
        }
)
public class Item extends BaseUuidEntity {

    @Column(name = "name")
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Slot slot;

    @Column
    private int stamina;

    @Column
    private int strength;

    @Column
    private int attack;

    @Column
    private int defence;

    @ManyToMany(mappedBy = "items")
    private List<Hero> heroes;
}
