package com.sample.jetty;

import javax.inject.Inject;
import javax.servlet.*;
import java.io.IOException;

public class TestFilter implements Filter {

    @Inject
    InjectedDAO dao;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("sds");
    }

}
