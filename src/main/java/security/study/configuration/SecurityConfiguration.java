package security.study.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import security.study.security.filter.AuthenticationLogFilter;
import security.study.security.filter.RequestValidationFilter;
import security.study.security.filter.SecretCodeAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final RequestValidationFilter requestValidationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic();

        http
                .addFilterBefore(requestValidationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthenticationLogFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new SecretCodeAuthenticationFilter(), BasicAuthenticationFilter.class);

        http
                .authorizeRequests().anyRequest().authenticated();

        return http.build();
    }
}
