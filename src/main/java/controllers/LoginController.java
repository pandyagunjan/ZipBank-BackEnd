package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import services.LoginServices;

@RestController
public class LoginController {

    @Autowired
    private LoginServices loginServices;


}
