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

    public ResponseEntity join(String email, String nick_name ,  String password, UserGrant user_grant){
        Members members = memberRepository.findByEmail(email);

        if(members != null){
            return new ResponseEntity(ReturnCode.exist_email,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Members create = new Members();
        create.setEmail(email);
        create.setNick_name(nick_name);
        create.setPassword(passwordEncoder.encode(password));
        create.setUser_grant(user_grant);

        System.out.println(create);

        return new ResponseEntity(memberRepository.save(create), HttpStatus.OK);
    }
//
//    public ResponseEntity login(String email, String pw){
//        Members members = memberRepository.findByEmail(email);
//
//        if(members == null){
//            return new ResponseEntity(ReturnCode.n_exist_email, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        String password = members.getPassword();
//
//        if(passwordEncoder.matches(pw, password)){
//            Map<String, Object> token_map = new HashMap<>();
//
//            String access_token = jwtTokenUtil.doGenerateToken(user);
//            String refresh_token = jwtTokenUtil.generateRefreshToken();
//
//            token_map.put("access_token", access_token);
//            token_map.put("refresh_token", refresh_token);
//
//            checkUser.setRefresh_token(refresh_token);
//            userRepository.save(checkUser);
//
//            LoginHistory loginHistory = new LoginHistory();
//            loginHistory.setTime(Main.getTime());
//            loginHistory.setUser_id(checkUser.getId());
//            loginHistoryRepository.save(loginHistory);
//
//            return new Result(200, token_map, null);
//        }else{
//            return new Result(427, null, "Password is Wrong");
//        }
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

}
