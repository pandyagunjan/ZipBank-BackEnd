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
import java.util.logging.Logger;

@RequestMapping("/profile")
@RestController
public class UserController {
    @Autowired
    private UserServices userServices;

    private final static Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> readById(@PathVariable Long id) throws Exception {
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

    @PutMapping(value = "/update/{id}/phone")
    public ResponseEntity<?> updatePhone(@RequestBody String phoneNumber,@PathVariable Long id) throws Exception {
        int response =userServices.updateUserPhoneNumber(id,phoneNumber);
        if(response ==0 )
            return new ResponseEntity<>(userServices.readUser(id), HttpStatus.OK);
        else if(response == 1 )
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
           return new ResponseEntity<>("Re-send the phone number", HttpStatus.BAD_REQUEST);
       // return new ResponseEntity<>(userServices.updateUserPhoneNumber(id,phoneNumber), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}/email")
    public ResponseEntity<?> updateEmail(@RequestBody String email,@PathVariable Long id) throws Exception {
        int response =userServices.updateUserEmailId(id,email);
        if(response ==0 )
            return new ResponseEntity<>(userServices.readUser(id), HttpStatus.OK);
        else if(response == 1 )
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Re-send the email-d", HttpStatus.BAD_REQUEST);
        // return new ResponseEntity<>(userServices.updateUserPhoneNumber(id,phoneNumber), HttpStatus.OK);
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