package vn.iotstar.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CharacterEncodingFilter implements Filter {
    private String encoding = StandardCharsets.UTF_8.name();
    private boolean forceEncoding;

    @Override
    public void init(FilterConfig config) {
        String configuredEncoding = config.getInitParameter("encoding");
        if (configuredEncoding != null && !configuredEncoding.isBlank()) {
            encoding = configuredEncoding;
        }
        forceEncoding = Boolean.parseBoolean(config.getInitParameter("forceEncoding"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (forceEncoding || request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
        }
        if (forceEncoding || response.getCharacterEncoding() == null) {
            response.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }
}