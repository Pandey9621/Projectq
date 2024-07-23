//package com.ideabytes.cors;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterConfig;
//import javax.servlet.http.*;
//import javax.servlet.*;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Configuration
//@EnableWebMvc
//public class CorsFilter implements Filter {
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
//	 */
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
//	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
//	 */
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//			throws IOException, ServletException {
//		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//		httpServletResponse.addHeader("Access-Control-Allow-Origin", "*"); // IMPORTANT: Allowed all the domains
//		httpServletResponse.addHeader("x-testRun-do", "12");
//		httpServletResponse.addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE, PUT, PATCH, HEAD");
//		httpServletResponse.addHeader("Access-Control-Allow-Headers",
//				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Origin, Authorization");
//		httpServletResponse.addHeader("Access-Control-Expose-Headers",
//				"Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Content-Disposition");
//		httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
//		httpServletResponse.addIntHeader("Access-Control-Max-Age", 3600);
//		if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
//			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//		} else {
//			filterChain.doFilter(httpServletRequest, httpServletResponse);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see javax.servlet.Filter#destroy()
//	 */
//	@Override
//	public void destroy() {
//
//	}
//}