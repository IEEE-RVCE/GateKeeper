package org.ieeervce.gatekeeper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/loginStatus")
public class LoginStatusController {
    @RequestMapping("/failed")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    void loginFailed(){
        return;
    }
    @RequestMapping("/success")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    void loginSuccess(){
        return;
    }
}
