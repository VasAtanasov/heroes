package bg.softuni.heroes.service.models.items;

import bg.softuni.heroes.data.enums.Slot;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDetailedServiceModel {
    private UUID id;
    private String name;
    private Slot slot;
    private int stamina;
    private int strength;
    private int attack;
    private int defence;
}
