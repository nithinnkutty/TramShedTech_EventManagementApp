package com.tramshedtech.eventmanagement.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * authentication
 */

public class AuthFliter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Business logic judgement
        System.out.println("Filters perform filtration");

        // Get uri
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String uri = ((HttpServletRequest) request).getRequestURI();

        System.out.println(uri);

        // Determine whether the currently accessed uri needs to be logged in before it can be operated.
        // let pass
        if(!anon(uri)){
            // Do not allow anonymous direct access, you also need to determine whether you are logged in, get the user id in the session
            if(servletRequest.getSession().getAttribute("uid")==null){
                // Not logged in, return result, go to log in
                // Get the response object
                HttpServletResponse servletResponse = (HttpServletResponse) response;

                // Setting the response header
                servletResponse.setContentType("application/json;charset=utf-8");

                // Data returned
                ResponseResult responseResult = new ResponseResult().setCode(401).setMessage("您还没登陆，请登录").setStatus(ResponseStatus.NO_LOGIN);

                // Convert an object to a string in JSON format: Jackson
                String json = new ObjectMapper().writeValueAsString(responseResult);

                // Returning data via response
                servletResponse.getWriter().write(json);

                // not let pass
                return;
            }

        }
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }

    // Determine if uri can be accessed anonymously (without logging in)
    public boolean anon(String uri){
        if (1 == 1) return true; // Temporary release of all requests
        // If it's the root path, just let it go.
        if(uri.equals("/")) return true;

        // All uri's that can be accessed anonymously
        List<String> anons = Arrays.asList("/login.html","/js","/css","/user/login");

        // Set the variable, the default is false, means it can not be returned anonymously
        // AtomicBoolean: Atomicxxx variables can be shared within the current method and the method's lambda expression
        AtomicBoolean flag = new AtomicBoolean(false);

        anons.forEach(str -> {
            if(uri.equals(str) || uri.startsWith(str)){
                flag.set(true);
            }
        });

        return flag.get();




    }
}
