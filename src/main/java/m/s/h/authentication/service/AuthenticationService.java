package m.s.h.authentication.service;

import m.s.h.authentication.ReturnCode;
import m.s.h.authentication.UserGrant;
import m.s.h.authentication.model.Members;
import m.s.h.authentication.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity join(String email, String nick_name ,  String password, UserGrant user_grant, String blog_name){
        Members members = memberRepository.findByEmail(email);

        if(members != null){
            return new ResponseEntity(ReturnCode.exist_email,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Members create = new Members();
        create.setEmail(email);
        create.setNick_name(nick_name);
        create.setPassword(passwordEncoder.encode(password));
        create.setUser_grant(user_grant);
        create.setBlog_name(blog_name);

        return new ResponseEntity(memberRepository.save(create), HttpStatus.OK);
    }

}
