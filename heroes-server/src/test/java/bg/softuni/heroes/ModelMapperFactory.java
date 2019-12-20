package bg.softuni.heroes;

import bg.softuni.heroes.util.ModelMapperWrapper;
import bg.softuni.heroes.util.ModelMapperWrapperImpl;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class ModelMapperFactory {

    public static ModelMapperWrapper getModelMapper() {
        return new ModelMapperWrapperImpl(new ModelMapper());
    }
}
