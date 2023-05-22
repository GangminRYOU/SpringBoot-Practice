package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
 @GetMapping("/")//첫번쨰 도메인 -> localhost:8080으로 들어오면 호출되는 url
    public String home(){
     return "home";
 }
}
