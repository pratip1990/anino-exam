package com.anino.leaderboardms.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeaderboardAdminFilter implements Filter{

	@Value("${admin.token:ASDSAADGEEGDASSD}")
	private String adminToken;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		
		String tokenHeader = null;
		String token = null;
	    if (headerNames != null) {
	        while (headerNames.hasMoreElements()) {
	            String name = headerNames.nextElement();
	            System.out.println("Header: " + name + " value:" + httpRequest.getHeader(name));
	            if("AUTHORIZATION".equals(name.toUpperCase())) {
	            	tokenHeader = name.toUpperCase();
	            	token = httpRequest.getHeader(name);
	            	break;
	            }
	            
	        }
	    }
		
	    String path = httpRequest.getServletPath();
	    System.out.println("path: " + path );
	    if(path.contains("/admin") && (StringUtils.isBlank(tokenHeader) || StringUtils.isBlank(token) || !adminToken.equals(token))) {
	    	httpResponse.setStatus(401);
	    }else {
	       	chain.doFilter(httpRequest, httpResponse);
	    }
	}

}
