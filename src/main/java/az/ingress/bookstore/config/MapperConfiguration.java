package az.ingress.bookstore.config;

import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.rest.dto.request.UserRequestDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(new PropertyMap<User, UserRequestDTO>() {
            @Override
            protected void configure() {
                skip(destination.getPassword());
            }
        });
        return modelMapper;
    }

}
