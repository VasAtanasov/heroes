package bg.softuni.heroes.web.models.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemCreateRequest {
    private String name;
    private String slot;
    private int stamina;
    private int strength;
    private int attack;
    private int defence;
}
