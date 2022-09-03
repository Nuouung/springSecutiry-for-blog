package security.study.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import security.study.security.service.JpaUserDetailsService;
import security.study.security.user.CustomUserDetails;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails customUserDetails = jpaUserDetailsService.loadUserByUsername(username);

        switch (customUserDetails.getUser().getAlgorithm()) {
            case BCRYPT:
                return checkPassword(customUserDetails, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(customUserDetails, password, sCryptPasswordEncoder);
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }

    private Authentication checkPassword(CustomUserDetails customUserDetails, String rawPassword, PasswordEncoder passwordEncoder) {

        if (passwordEncoder.matches(rawPassword, customUserDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    customUserDetails.getUsername(),
                    customUserDetails.getPassword(),
                    customUserDetails.getAuthorities());
        }

        throw new BadCredentialsException("Bad credentials");
    }
}
