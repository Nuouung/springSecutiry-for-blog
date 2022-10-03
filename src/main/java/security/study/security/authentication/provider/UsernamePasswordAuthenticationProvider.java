package security.study.security.authentication.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import security.study.security.authentication.proxy.AuthenticationServerProxy;
import security.study.security.authentication.token.UsernamePasswordAuthentication;

@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerProxy authenticationServerProxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        authenticationServerProxy.sendAuth(username, password);

        // 인증 과업을 인증 서버에 전가하고 그냥 통과시킴
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class
                .isAssignableFrom(authentication);
    }
}
