package runner.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import runner.services.AccountServices;

@Profile("test")
@Configuration

public class AccountServiceTestConfiguration {
    @Bean
    @Primary
    public AccountServices accountServices() {
        return Mockito.mock(AccountServices.class);
    }
}
