package runner.security.controllers;

import com.mifmif.common.regex.Generex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import runner.entities.Account;
import runner.entities.Login;
import runner.security.models.AuthenticationResponse;
import runner.security.utilities.JwtUtil;
import runner.services.AccountServices;
import runner.services.CustomerServices;
import runner.services.LoginServices;

import java.util.Set;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginServices loginServices;
    @Autowired
    private AccountServices accountServices;

    //jwt authentication
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody Login login) throws Exception{
        try{
            Login testLogin = login;
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );
        }
        catch (BadCredentialsException e){ //this exception message should be linked to frontend
            throw new Exception("Incorrect username or password", e);
        }
        addRandomUrlToAccounts(login);
        final UserDetails userDetails = loginServices.loadUserByUsername(login.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    //adding the random URL to the accounts
    public void addRandomUrlToAccounts(Login login){
        Set<Account> accountSet = accountServices.getAllAccounts(login.getUsername());
        accountSet.stream().forEach(a->a.setEncryptedUrl(generateRandomUrl()));
    }

    //generate 35-40 random characters
    public String generateRandomUrl() {
        Generex generex = new Generex("[A-Za-z0-9]{35,40}");
        String randomString = generex.random();
        return randomString;
    }

}