package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// Controller는 Controller 어노테이션 필수!
@Controller
public class HelloController {

    // @GetMapping은
    // web application에서 /hello 로 진입할 경우
    // 해당 메소드를 호출해줌!
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello Sengna!");
        // resources/templates의 hello.html 반환하는거라서 이름 맞춰줘야함!!!
        return "hello";
    } // hello()

    @GetMapping("hello-mvc")
    // 외부에서 param을 받아줄 것임!
    // 모델에 담아서 view rendering에 쓰기!
    // public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // param으로 넘어온 name
        model.addAttribute("name", name);

        return "hello-template";
    } // helloMvc()

    @GetMapping("hello-string")
    // @ResponseBody = http에서의 body 부분에 데이터를 직접 넣어주겠다는 의미
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // name이 spring이라면 -> "hello spring"
        // 이전 mvc+템플릿 엔진과의 차이점 = view 없이 해당 문자가 그대로 내려감
    } // helloString()

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);

        // 객체 반환
        return hello;
    }

    // static class 선언
    static class Hello {

        // private이기 때문에 외부에서 사용이 불가능함
        // -> 라이브러리에서 사용하거나 메소드를 활용해서 사용이 가능함
        private String name;

        public String getName() {
            return name;
        } // getter

        public void setName(String name) {
            this.name = name;
        } // setter

    } // end class

} // end class
