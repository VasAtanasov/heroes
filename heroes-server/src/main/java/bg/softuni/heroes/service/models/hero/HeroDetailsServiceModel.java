package bg.softuni.heroes.service.models.hero;

import bg.softuni.heroes.data.enums.Gender;
import bg.softuni.heroes.service.models.item.ItemDetailedServiceModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroDetailsServiceModel {
    private UUID id;
    private String name;
    private Gender gender;
    private int level;
    private int stamina;
    private int strength;
    private int attack;
    private int defence;
    @Builder.Default
    private List<ItemDetailedServiceModel> items = new ArrayList<>();
}
