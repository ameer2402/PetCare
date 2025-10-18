package com.examly.springapp.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor to inject JwtUtils and UserDetailsService.
     *
     * @param jwtUtils           Utility class for JWT operations.
     * @param userDetailsService Service to load user details.
     */
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filters incoming HTTP requests to authenticate the user based on JWT token.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain.
     * @throws ServletException If an error occurs during filtering.
     * @throws IOException      If an IO error occurs during filtering.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Retrieve the Authorization header from the HTTP request
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // If the Authorization header is missing or does not start with "Bearer ",
        // proceed with the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header
        final String token = authHeader.substring(7);
        final String username = jwtUtils.extractUsername(token);

        // If the username is not null and there is no authentication information in the
        // SecurityContextHolder
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details using the username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validate the token
            if (jwtUtils.isValidateToken(token, userDetails)) {
                // Create an authentication token using the user details and set it in the
                // SecurityContextHolder
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // Extract role from token and check access permissions
                String role = jwtUtils.extractUserRole(token);
                if (role.equals("ROLE_ADMIN") && !request.getRequestURI().startsWith("/api/appointments")
                        && !request.getRequestURI().startsWith("/api/feedback")) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return;
                } else if (role.equals("ROLE_PETOWNER") && !request.getRequestURI().startsWith("/api/pet")
                        && !request.getRequestURI().startsWith("/api/appointments")
                        && !request.getRequestURI().startsWith("/api/feedback")) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return;
                }
            }
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}