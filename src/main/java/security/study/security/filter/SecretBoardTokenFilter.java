package security.study.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import security.study.repository.SecretBoardTokenRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SecretBoardTokenFilter implements Filter {

    private final SecretBoardTokenRepository tokenRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("SecretBoardTokenFilter 초기화 완료");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (isTargetRequest(httpServletRequest) && !isValidToken(httpServletRequest)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("SecretBoardTokenFilter 종료 완료");
        Filter.super.destroy();
    }

    private boolean isTargetRequest(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().equals("/secret/password") && httpServletRequest.getMethod().equals("POST");
    }

    private boolean isValidToken(HttpServletRequest httpServletRequest) {
        List<String> tokenList = tokenRepository.getTokenList();
        String secretBoardToken = (String) httpServletRequest.getAttribute("secret-board-token");

        for (String token : tokenList) {
            if (token.equals(secretBoardToken)) return true;
        }

        return false;
    }
}
