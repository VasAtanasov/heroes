package bg.softuni.heroes.web.models.response.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ItemListModel {
    private UUID id;
    private String name;
    private String slot;
    private int stamina;
    private int strength;
    private int attack;
    private int defence;
}
