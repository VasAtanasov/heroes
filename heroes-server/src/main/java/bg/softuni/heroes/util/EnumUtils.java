package bg.softuni.heroes.util;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.stream.Stream;

@UtilityClass
public final class EnumUtils {

    public static <T extends Enum<T>> Optional<T> fromString(String string, Class<T> enumClass) {
        return Stream.of(enumClass.getEnumConstants())
                .filter(t -> t.name().equalsIgnoreCase(string))
                .findAny();
    }

}
