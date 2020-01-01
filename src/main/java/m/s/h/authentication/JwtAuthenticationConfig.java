package m.s.h.authentication;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@ToString
public final class JwtAuthenticationConfig {

    @Value("${security.jwt.url:/login}")
    private String url;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration; // default 24 hours

    //TODO jwt secret 프로퍼티 파일로 변경해서 올려야한다
    @Value("${security.jwt.secret:12345}")
    private String secret;

}
