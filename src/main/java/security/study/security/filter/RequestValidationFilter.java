package security.study.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RequestValidationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("필터 초기화 : {}", this.getClass());
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String secretAccessCode = request.getHeader("Secret-Access-Code");

        if (secretAccessCode == null || secretAccessCode.isBlank() || !secretAccessCode.equals("secret")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("필터 종료 : {}", this.getClass());
        Filter.super.destroy();
    }
}
