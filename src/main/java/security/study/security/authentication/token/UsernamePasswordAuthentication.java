package security.study.security.authentication.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {
    // 인증되지 않은, 즉 provider의 매개변수로 들어가는 authentication 객체
    public UsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    // 인증을 마친, 즉 provider에서 빠져 나오는 authentication 객체
    // authentication manager는 authentication이 authority를 가지고 있으면 인증이 되었다 간주한다.
   public UsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
