package com.lifefit.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

	private ServletContext context;
	private FilterConfig filterConfig = null;
	
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String uri = req.getRequestURI();
		this.context.log("Requested resource: "+uri);
		
		if(req.getSession().getAttribute("idPerson") == null && 
				(!uri.endsWith("index.jsp") && !uri.endsWith("Login"))){
			this.context.log("Unauthorized access request...");
			res.sendRedirect("index.jsp");
		}
		else 		
			// pass the request along the filter chain
			chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
		this.context = this.filterConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}

}
