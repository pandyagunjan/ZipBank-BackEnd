package runner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import runner.entities.Account;
import runner.services.AccountServices;
import runner.services.CustomerServices;
import runner.services.LoginServices;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TestHomePageDeleteLater {
    @Autowired
    CustomerServices customerServices;

    @Autowired
    AccountServices accountServices;

    @Autowired
    LoginServices loginServices;

    @GetMapping(value = "/")
    public String homePage(){
        return "Hello World";
    }


}