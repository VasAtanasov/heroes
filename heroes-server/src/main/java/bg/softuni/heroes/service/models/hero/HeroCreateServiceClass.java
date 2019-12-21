package bg.softuni.heroes.service.models.hero;

import bg.softuni.heroes.data.enums.Gender;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroCreateServiceClass {
    private String name;
    private Gender gender;
}
