package no.telenor.ai.interview.config;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class LegacyRequestHeaderFilter extends OncePerRequestFilter {

    private String headerValue;

    @PostConstruct
    void initialize() {
        headerValue = StringUtils.defaultIfBlank(System.getProperty("legacy.filter.name"), "legacy-servlet-filter");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("X-Legacy-Filter", headerValue);
        filterChain.doFilter(request, response);
    }
}
