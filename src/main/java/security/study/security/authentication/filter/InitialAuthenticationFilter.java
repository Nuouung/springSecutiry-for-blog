package security.study.security.authentication.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security.study.security.authentication.token.OtpAuthentication;
import security.study.security.authentication.token.UsernamePasswordAuthentication;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
//@RequiredArgsConstructor
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.signing.key}")
    private String signingKey;

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        if (password != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthentication(username, password));
        }

        if (code != null) {
            Authentication authentication = authenticationManager.authenticate(
                    new OtpAuthentication(username, code));

            SecretKey secretKey = Keys.hmacShaKeyFor(
                    signingKey.getBytes(StandardCharsets.UTF_8));

            String jwtToken = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(secretKey)
                    .compact();

            response.setHeader("Authentication", jwtToken);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // ?????? ?????? ?????? ????????? ?????? ??????.
        // should not be filtered when condition is like [??????]
        // ??? [??????]??? ?????? ????????? ?????? ?????????.

        // ?????? ????????? login ????????? ????????? = true ??????
        // ???, login ????????? ?????? ??????, ????????? ?????? ?????? ???
       return !request.getServletPath().equals("/login");
    }
}
