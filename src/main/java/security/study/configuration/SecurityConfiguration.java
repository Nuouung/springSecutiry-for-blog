package security.study.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import security.study.security.authority.CustomGrantedAuthority;
import security.study.security.service.InMemoryUserDetailsService;
import security.study.security.user.CustomUser;

import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        CustomUser user1 = new CustomUser("jinseok", "1234", new CustomGrantedAuthority("read"));
        CustomUser user2 = new CustomUser("roni", "1234", new CustomGrantedAuthority("read"));
        return new InMemoryUserDetailsService(List.of(user1, user2));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
