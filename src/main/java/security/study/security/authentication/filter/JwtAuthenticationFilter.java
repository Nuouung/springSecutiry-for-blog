package security.study.security.authentication.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security.study.security.authentication.token.UsernamePasswordAuthentication;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authentication");

        SecretKey secretKey = Keys.hmacShaKeyFor(
                signingKey.getBytes(StandardCharsets.UTF_8));

        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        Claims claims = jwtParser.parseClaimsJws(jwtToken)
                .getBody();

        String username = claims.get("username").toString();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
        UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(username, null, List.of(authority));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}
