package runner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runner.entities.Login;
import runner.services.LoginServices;

@RequestMapping("/login")
@RestController
public class LoginController {

    @Autowired
    private LoginServices loginServices;

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Login> readById(@PathVariable Long id) throws Exception {
        if(new ResponseEntity<>(loginServices.readLogin(id), HttpStatus.OK) == null) throw new Exception("Error , the user id is null") ;
        else
            return new ResponseEntity<>(loginServices.readLogin(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Login> create(@RequestBody Login login) {
        Login loginResult =loginServices.createLogin(login);
        if( loginResult == null)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        else
            return new ResponseEntity<>(loginResult, HttpStatus.OK);

    }

}