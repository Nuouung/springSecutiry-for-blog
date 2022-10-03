package security.study.security.authentication.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import security.study.model.User;

@Component
@RequiredArgsConstructor
public class AuthenticationServerProxy {

    @Value("${auth.server.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public void sendAuth(String username, String password) {
        String url = baseUrl + "/user/auth";

        User body = new User();
        body.setUsername(username);
        body.setPassword(password);

        HttpEntity<User> request = new HttpEntity<>(body);
        restTemplate.postForEntity(url, request, Void.class);
    }

    public boolean sendOtp(String username, String code) {
        String url = baseUrl + "/otp/check";

        User body = new User();
        body.setUsername(username);
        body.setCode(code);

        HttpEntity<User> request = new HttpEntity<>(body);
        ResponseEntity<Void> response = restTemplate.postForEntity(url, body, Void.class);

        return response
                .getStatusCode().equals(HttpStatus.OK);
    }
}
