package com.sample.jetty;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by tempus on 08/02/2016.
 */
public class TestFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("sds");
    }

}
