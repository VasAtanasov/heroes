package bg.softuni.heroes.web.models.response.item;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailsResponseModel {
    private UUID id;
    private String name;
    private String  slot;
    private int stamina;
    private int strength;
    private int attack;
    private int defence;
}
