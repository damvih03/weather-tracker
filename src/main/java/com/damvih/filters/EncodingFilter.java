package com.damvih.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(ENCODING);
        servletResponse.setCharacterEncoding(ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
