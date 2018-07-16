package com.zero.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Usage: refer to FilterConf/loginFilter()
 * 
 * @author Louisling
 * @version 2018-07-10
 */
public class LoginFilter implements Filter {
    static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Front side need to set header
        //xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
        //xhr.withCredentials = true;
        //xhr.crossDomain = true;

        //HttpUtil.prtRequest(HttpUtil.getRequest());
        HttpServletResponse resp = ((HttpServletResponse) response);
        resp.setHeader("Access-Control-Allow-Origin", "*"); // http://192.168.101.59:9002 
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "languageToken, Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        
        chain.doFilter(request, response);
    }
}
