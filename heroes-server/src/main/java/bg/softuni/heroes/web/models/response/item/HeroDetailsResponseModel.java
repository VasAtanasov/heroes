package bg.softuni.heroes.web.models.response.item;

import bg.softuni.heroes.data.enums.Gender;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroDetailsResponseModel {
    private UUID id;
    private String name;
    private Gender gender;
    private int level;
    private int stamina;
    private int strength;
    private int attack;
    private int defence;
    @Builder.Default
    private List<ItemDetailsResponseModel> items = new ArrayList<>();
}
