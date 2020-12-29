package runner.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestHomePageDeleteLater {

    @RequestMapping("/")
    public String home()
    {
        return "Hello World";
    }
}
