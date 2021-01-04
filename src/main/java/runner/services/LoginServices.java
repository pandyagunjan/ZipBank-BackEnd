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
public class LoginServices implements UserDetailsService {

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Login createLogin(Login login1) {
        Login encryptedLogin = new Login();
        List<Login> allLogins= loginRepo.findAll();
        List result = allLogins.stream().filter((login) -> login.getUsername().equals(login1.getUsername())).collect(Collectors.toList());
        if(result.size()==0) {
            encryptedLogin.setUser(login1.getUser());
            encryptedLogin.setPassword(bCryptPasswordEncoder.encode(login1.getPassword()));
            return loginRepo.save(encryptedLogin);
        }
        return null;
    }

    //need another password authentication done before allowing user to update their password
    public Login updatePassword(Long userId, Login login) {
        Login updatedLogin = loginRepo.findLoginById(userId);
        updatedLogin.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));
        return loginRepo.save(updatedLogin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepo.findLoginByUsername(username);
        return new User(login.getUsername(), login.getPassword(),new ArrayList<>()); //ArrayList is typically for the authority but not using this feature
    }

    public Login readLogin(Long id){
        return loginRepo.findLoginById(id);
    }

    public Login findLoginByUsername(String username){
        return loginRepo.findLoginByUsername(username);
    }

    public Boolean logOut() {
        // Login for logOut
        return true;
    }


//Lock after 3 attempts
//Security questions
//Forgot username
//Forgot password

}