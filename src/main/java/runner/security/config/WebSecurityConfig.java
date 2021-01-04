package runner.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import runner.security.filters.JwtAuthorizationFilter;
import runner.services.LoginServices;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity //allows Spring to find and automatically apply the class to the global Web Security.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginServices loginServices;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    //dependency injects into loginServices
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //dependency injects into loginController
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //dependency injects into AuthenticationController
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginServices).passwordEncoder(bCryptPasswordEncoder());
    }

    //allowing user to post to authenticate since spring security is placed on all request
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/authenticate","/").permitAll() //permit everybody for this endpoint
                .anyRequest().authenticated() //all other request requires authentication
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //jwt is stateless, asking Spring to not create sessions for each request
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); //asking Spring to use jwtAuthorizationFilter before UsernamePasswordAuthenticationFilter is called
    }

    /*    @Override //creating own form for login
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("insertHomePageUrlHere").permitAll()
                .and()
                .logout().invalidateHttpSession(true) //handles logout
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("insertUrlForLogoutHere"))
                .logoutSuccessUrl("insertUrlSuccessPageHere").permitAll();
    }*/

}