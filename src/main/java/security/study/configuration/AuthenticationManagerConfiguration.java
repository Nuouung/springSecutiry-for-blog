//package security.study.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import security.study.security.authentication.provider.OtpAuthenticationProvider;
//import security.study.security.authentication.provider.UsernamePasswordAuthenticationProvider;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class AuthenticationManagerConfiguration {
//
//    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
//    private final OtpAuthenticationProvider otpAuthenticationProvider;
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
//        return auth
//                .authenticationProvider(otpAuthenticationProvider)
//                .authenticationProvider(usernamePasswordAuthenticationProvider)
//                .build();
//    }
//}
