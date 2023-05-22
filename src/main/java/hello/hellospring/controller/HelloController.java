package hello.hellospring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
    model.addAttribute("data", "Spring!!");
    return "hello";}

    @GetMapping("hello-mvc")
    //parameter를 받아보자
    public String helloMvc(  String name, Model model){
    //이전에는 "Spring"이라고 직접 받았다."
        //model에 담으면 view에서 Rendering할떄 쓴다.
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    //가장 중요한 것
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloAPI(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;
        //Getter
        public String getName(){
            return this.name;
        }
        //Setter
        public void setName(String name){
            this.name = name;
        }
    }
}
