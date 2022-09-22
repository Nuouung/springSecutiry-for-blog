package security.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import security.study.repository.SecretBoardTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller("/secret")
@RequiredArgsConstructor
public class SecretBoardController {

    private final SecretBoardTokenRepository tokenRepository;

    @GetMapping("password")
    public String getPasswordPage(HttpServletRequest request) {

        String token = UUID.randomUUID().toString();

        request.setAttribute("secret-board-token", token);
        tokenRepository.save(token);

        return "secret/password";
    }

    @PostMapping("password")
    public String postPassword() {
        return "redirect:/secret/detail";
    }

    @GetMapping("detail")
    public String getDetailPage() {
        return "secret/detail";
    }
}
