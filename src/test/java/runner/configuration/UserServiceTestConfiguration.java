package runner.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import runner.services.CustomerServices;

@Profile("test")
@Configuration
public class UserServiceTestConfiguration {
    @Bean
    @Primary
    public CustomerServices userServices() {
        return Mockito.mock(CustomerServices.class);
    }

}