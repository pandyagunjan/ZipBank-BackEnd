package runner.security.models;

import org.springframework.security.authentication.AuthenticationManager;

public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt){
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
