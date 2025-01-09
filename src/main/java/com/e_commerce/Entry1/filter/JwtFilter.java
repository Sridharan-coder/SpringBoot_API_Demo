package com.e_commerce.Entry1.filter;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.e_commerce.Entry1.configurations.MyUserDetailService;
import com.e_commerce.Entry1.dao_imple.JWTServices;
import com.e_commerce.Entry1.dto.Seller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTServices jwtServices;
	
	@Autowired
	ApplicationContext context;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String userName=null;
		
		if(authHeader !=null && authHeader.startsWith("Bearer")) {
			token=authHeader.substring(7);
			userName=jwtServices.extractUsername(token);
		}
		
		if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails details= context.getBean(MyUserDetailService.class).loadUserByUsername(userName);
			
			if(jwtServices.validateToken(token, details.getUsername())) {
				System.out.println("Details --> "+details);
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(details,null);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				System.out.println(authToken);
			}
			
		}
		
		System.out.println(token+"++++++++++++++++++++++++++++++++++"+userName);
		filterChain.doFilter(request, response);
		
	}

}
