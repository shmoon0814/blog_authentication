package m.s.h.authentication.controller;

import m.s.h.authentication.UserGrant;
import m.s.h.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;

@RestController
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth/v1/join")
    public ResponseEntity join(String email, String nick_name , String password, UserGrant user_grant, String blog_name){
        return authenticationService.join(email, nick_name, password, user_grant, blog_name);
    }

    @GetMapping("/test")
    public void test(){
        System.out.println("테스트진임");

    }

    @GetMapping("/auth/v1/getUserIdDisplayName")
    public ResponseEntity getUserIdByDisplay_name(@RequestParam String display_name){
        return authenticationService.getUserIdByDisplay_name(display_name);
    }

//
//    @PostMapping("/auth/v1/login")
//    public ResponseEntity login(String email, String password){
//        return authenticationService.login(email, password);
//    }
//


}
