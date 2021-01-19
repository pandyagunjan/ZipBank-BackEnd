package runner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import runner.entities.Login;
import runner.repositories.LoginRepo;

import java.util.ArrayList;

@Service
public class UserDetailServices implements UserDetailsService {
    @Autowired
    private LoginRepo loginRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepo.findLoginByUsername(username);
        return new User(login.getUsername(), login.getPassword(),new ArrayList<>()); //ArrayList is typically for the authority but not using this feature
    }
}
