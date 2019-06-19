package com.palestra.notaria.ws;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.palestra.notaria.util.JonasBot;

public class WSFilter implements Filter {

	private ServletContext context;
	
    public WSFilter() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//jonasbot 
    	new JonasBot();
    	System.out.printf("=====> notarias.filtering-starting at %s%n",dateFormat.format(new Date()));
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		System.out.printf("The request to %s came from %s and the destination is: %s%n", httpRequest.getRequestURL(), request.getRemoteHost()+":"+request.getRemotePort(), request.getServerName()+":"+request.getServerPort());
		chain.doFilter(request, response);
		
//		if(httpRequest.getRemoteAddr().equals(context.getInitParameter("myIp"))
//		||httpRequest.getRemoteAddr().equals(context.getInitParameter("myIpV6"))){
//			System.out.printf("Request is from I expected, so ...%n", request.getRemoteAddr(), httpRequest.getRequestURL());
//			chain.doFilter(request, response);
//		}else{
//			System.out.printf("Somebody is trying to do nasty things and wake me up, so redirect to ...");
//			String requestURI = httpRequest.getRequestURI();
//			String toReplace = requestURI.substring(requestURI.indexOf("/notaria/"));
//            String newURI = requestURI.replace(toReplace, "default.jsp");
//            request.getRequestDispatcher(newURI).forward(request, response);
//		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		context = fConfig.getServletContext();
		
	}

}
