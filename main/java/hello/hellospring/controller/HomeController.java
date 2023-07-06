package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // localhost:8080 진입 시 첫 화면 -> home.html 호출
    @GetMapping("/")
    public String home() {
        return "home";
    } // home()

} // end class
