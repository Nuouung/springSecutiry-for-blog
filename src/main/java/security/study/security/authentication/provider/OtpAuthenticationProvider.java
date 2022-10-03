package security.study.security.authentication.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import security.study.security.authentication.proxy.AuthenticationServerProxy;
import security.study.security.authentication.token.OtpAuthentication;

@Component
@RequiredArgsConstructor
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerProxy authenticationServerProxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String code = authentication.getCredentials().toString();

        boolean result = authenticationServerProxy.sendOtp(username, code);
        if (result) return new OtpAuthentication(username, code);

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class
                .isAssignableFrom(authentication);
    }
}
