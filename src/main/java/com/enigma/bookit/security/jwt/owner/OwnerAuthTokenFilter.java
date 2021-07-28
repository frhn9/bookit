package com.enigma.bookit.security.jwt.owner;

import com.enigma.bookit.security.jwt.customer.CustomerJwtUtils;
import com.enigma.bookit.security.services.owner.OwnerDetailsServiceImpl;
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

public class OwnerAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    OwnerJwtUtils ownerJwtUtils;

    @Autowired
    OwnerDetailsServiceImpl ownerService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(httpServletRequest);
            if(jwt != null && ownerJwtUtils.validateJwtToken(jwt)){
                String username = ownerJwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails owner = ownerService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(owner, null, owner.getAuthorities());
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
            return headerAuth.substring(12, headerAuth.length());
        }
        return null;
    }
}
