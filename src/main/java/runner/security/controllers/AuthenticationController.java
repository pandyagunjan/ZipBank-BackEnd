package runner.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import runner.entities.Login;
import runner.security.models.AuthenticationResponse;
import runner.security.utilities.JwtUtil;
import runner.services.LoginServices;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginServices loginServices;

    //jwt authentication
    @PostMapping(value = "/authenticate" ) //endpoint is still up for debate
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
        final UserDetails userDetails = loginServices.loadUserByUsername(login.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}