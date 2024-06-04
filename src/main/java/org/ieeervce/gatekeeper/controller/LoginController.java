package org.ieeervce.gatekeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login1")
    String login(){
        return "hi";
    }
}
