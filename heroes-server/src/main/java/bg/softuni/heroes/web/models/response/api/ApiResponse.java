package bg.softuni.heroes.web.models.response.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private Boolean success;
    private String message;
}
