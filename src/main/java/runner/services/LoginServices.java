package runner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import runner.entities.Login;
import runner.repositories.LoginRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoginServices{ //} implements UserDetailsService { <--Moved to UserDetailServices

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //need another password authentication done before allowing user to update their password
    public Login updatePassword(Long userId, Login login) {
        Login updatedLogin = loginRepo.findLoginById(userId);
        updatedLogin.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));
        return loginRepo.save(updatedLogin);
    }


//Lock after 3 attempts
//Security questions
//Forgot username
//Forgot password

}