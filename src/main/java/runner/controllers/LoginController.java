package runner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import runner.services.LoginServices;

@RestController
public class LoginController {

    @Autowired
    private LoginServices loginServices;


}
