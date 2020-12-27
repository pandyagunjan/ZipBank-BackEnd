package runner.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import runner.entities.User;
import runner.services.UserServices;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserServices userServices;

    private final static Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<User> readById(@PathVariable Long id) throws Exception {
        logger.log(Level.INFO, "This is an information !");
        logger.log(Level.SEVERE, "Terrible Error!");
        logger.log(Level.WARNING, "Not So Bad Error , Its Warning!");
        if(userServices.readUser(id) == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(userServices.readUser(id), HttpStatus.OK);
    }
    @PostMapping(value = "/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        user=userServices.createUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(user,responseHeaders, HttpStatus.CREATED);

        //return new ResponseEntity<>(userServices.createUser(user), HttpStatus.CREATED);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> update(@RequestBody User user,@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(userServices.updateUser(id,user), HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(userServices.deleteUser(id), HttpStatus.OK);
    }
    @GetMapping(value = "/home")
    public String displayHome() {
        return "Hello World";
    }
}