package vn.maxtrann.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping("login")
    public String index() {
        // trả về file login.html trong thư mục templates
        return "login";  
    }

    @GetMapping("user/profile")
    public String profile() {
        // trả về file profile.html trong thư mục templates
        return "profile";  
    }
}

