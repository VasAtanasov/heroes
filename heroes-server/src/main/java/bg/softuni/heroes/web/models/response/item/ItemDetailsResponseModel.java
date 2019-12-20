package bg.softuni.heroes.web.models.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
