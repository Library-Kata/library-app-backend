package com.library.app.security;

import com.library.app.service.impl.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * JWT Authentication Filter for validating JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        List<String> roles = null;

        // Check if the header contains a token
        if (header != null && header.startsWith("Bearer ")) {
            jwtToken = header.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromJWT(jwtToken);
                roles = jwtTokenUtil.getRolesFromJWT(jwtToken);
            } catch (ExpiredJwtException e) {
                logger.warn("Token is expired");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Token has expired. Please log in again.");
                return;
            } catch (JwtException e) {
                logger.warn("Token is invalid");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Token is invalid. Please authenticate.");
                return;
            } catch (Exception e) {
                logger.error("Error during authentication", e);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.getWriter().write("An internal error occurred.");
                return;
            }
        }

        // Validate the token and load user details
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken)) {
                // Set authentication in the security context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Generate a new token if the request is valid
                String newToken = jwtTokenUtil.generateToken(username,roles);
                response.setHeader("Authorization", "Bearer " + newToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
