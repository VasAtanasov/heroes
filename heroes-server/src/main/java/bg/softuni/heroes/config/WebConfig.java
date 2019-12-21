package bg.softuni.heroes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String URL_INDEX = "/";
    public static final String URL_ERROR_UNAUTHORIZED = "/unauthorized";

    public static final String URL_ADMIN_BASE = "/admin";
    public static final String URL_ADMIN_USERS = URL_ADMIN_BASE + "/users";

    private static final String URL_USER_BASE = "/user";
    public static final String URL_USER_REGISTER = URL_USER_BASE + "/register";
    public static final String URL_USER_LOGIN = URL_USER_BASE + "/login";
    public static final String URL_USER_LOGOUT = URL_USER_BASE + "/logout";
    public static final String URL_USER_HOME = URL_USER_BASE + "/home";
    public static final String URL_USER_PROFILE = URL_USER_BASE + "/profile";

    public static final String URL_API_BASE = "/api";

    public static final String URL_ITEMS_BASE = "/items";

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                URL_API_BASE,
                HandlerTypePredicate.forAnnotation(RestController.class)
        );
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//    }
}
