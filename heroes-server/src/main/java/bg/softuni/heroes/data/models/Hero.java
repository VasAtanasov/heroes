package bg.softuni.heroes.data.models;

import bg.softuni.heroes.data.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "heroes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_heroes_name", columnNames = {"name"})
        }
)
public class Hero extends BaseUuidEntity {

    private static final int VALID_HERO_NAME_LENGTH = 32;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(
            name = "id",
            foreignKey = @ForeignKey(name = "fk_users_id"),
            columnDefinition = "BINARY(16)"
    )
    private User user;

    @Column(name = "name", nullable = false, length = VALID_HERO_NAME_LENGTH)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private int level;

    @Column
    private int stamina;

    @Column
    private int strength;

    @Column
    private int attack;

    @Column
    private int defence;

    @ManyToMany(
            targetEntity = Item.class,
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "hero_items",
            joinColumns = @JoinColumn(
                    name = "hero_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_hero_items_heroes_id")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "item_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_hero_items_items_id")
            )
    )
    private List<Item> items = new ArrayList<>();

}
