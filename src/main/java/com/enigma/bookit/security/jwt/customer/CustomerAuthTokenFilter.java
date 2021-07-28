package com.enigma.bookit.security.jwt.customer;

import com.enigma.bookit.security.services.customer.CustomerDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    CustomerJwtUtils customerJwtUtils;

    @Autowired
    CustomerDetailsServiceImpl customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerAuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(httpServletRequest);
            if(jwt != null && customerJwtUtils.validateJwtToken(jwt)){
                String username = customerJwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails customer = customerService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customer, null, customer.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
             }
        } catch (Exception e){
            logger.error("Can't set user authentication: {}", e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(13, headerAuth.length());
        }
        return null;
    }
}
