package saop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saop.case1.MethodLog;

@RestController
public class HelloController {

    @RequestMapping("/")
    @MethodLog
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
