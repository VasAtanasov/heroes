package bg.softuni.heroes.web.models.factories;

import bg.softuni.heroes.web.models.response.api.ApiResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiResponseFactory {

    public static ApiResponse buildApiResponse(boolean isSuccessful, String message) {
        return ApiResponse.builder()
                .success(isSuccessful)
                .message(message)
                .build();
    }
}
