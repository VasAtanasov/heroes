package bg.softuni.heroes.data.models;

import bg.softuni.heroes.data.enums.Slot;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Builder
    public Item(UUID id, String name, Slot slot, int stamina, int strength, int attack, int defence) {
        super(id);
        this.name = name;
        this.slot = slot;
        this.stamina = stamina;
        this.strength = strength;
        this.attack = attack;
        this.defence = defence;
        this.heroes = new ArrayList<>();
    }
}
