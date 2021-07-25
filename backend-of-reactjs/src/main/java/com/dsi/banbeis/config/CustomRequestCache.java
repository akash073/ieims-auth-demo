package com.dsi.banbeis.config;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/*
public class CustomRequestCache extends HttpSessionRequestCache {
    @Override
    public void saveRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        System.out.println("Saving request to " + httpServletRequest.getRequestURI());

        if(httpServletRequest.getRequestURI().equals("/authenticate")){
            super.saveRequest(httpServletRequest, httpServletResponse);
        }else{
            httpServletRequest.getSession().invalidate();
        }



    }

    @Override
    public HttpServletRequest getMatchingRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
         System.out.println("Returning request for " + httpServletRequest.getRequestURI());
         return super.getMatchingRequest(httpServletRequest, httpServletResponse);
    }
}*/
