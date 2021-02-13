package runner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import runner.services.LoginServices;

@RestController
public class LoginController {

    @Autowired
    private LoginServices loginServices;

    //Remove if not needed
//    @GetMapping(value = "/read/{id}")
//    public ResponseEntity<Login> readById(@PathVariable Long id) throws Exception {
//        if(new ResponseEntity<>(loginServices.readLogin(id), HttpStatus.OK) == null) throw new Exception("Error , the user id is null") ;
//        else
//            return new ResponseEntity<>(loginServices.readLogin(id), HttpStatus.OK);
//    }

    //Remove if not needed
//    @PostMapping(value = "/create")
//    public ResponseEntity<Login> create(@RequestBody Login login) {
//        Login loginResult =loginServices.createLogin(login);
//        if( loginResult == null)
//            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
//        else
//            return new ResponseEntity<>(loginResult, HttpStatus.OK);
//    }


}