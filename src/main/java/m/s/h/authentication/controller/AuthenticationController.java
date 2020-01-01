package m.s.h.authentication.controller;

import m.s.h.authentication.UserGrant;
import m.s.h.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/auth/v1/join")
    public ResponseEntity join(String email, String nick_name , String password, UserGrant user_grant){
        return authenticationService.join(email, nick_name, password, user_grant);
    }
//
//    @PostMapping("/auth/v1/login")
//    public ResponseEntity login(String email, String password){
//        return authenticationService.login(email, password);
//    }
//


}
