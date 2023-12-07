package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
@CrossOrigin(origins = "http://localhost:3000") //CORS ERROR 해결
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }
}

