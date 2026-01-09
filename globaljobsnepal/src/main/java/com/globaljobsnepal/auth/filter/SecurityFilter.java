package com.globaljobsnepal.auth.filter;

import com.globaljobsnepal.auth.entity.Role;
import com.globaljobsnepal.auth.entity.User;
import com.globaljobsnepal.auth.enums.Roles;
import com.globaljobsnepal.auth.service.AppUserDetailsService;
import com.globaljobsnepal.auth.service.JwtService;
import com.globaljobsnepal.auth.service.contract.UserService;
import com.globaljobsnepal.core.enums.Status;
import com.globaljobsnepal.utils.AppConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String bearerToken = request.getHeader("Authorization");
            String username = null;
            String token = null;

            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7);
                username = jwtService.extractUsername(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                User userByEmail = userService.findByEmail(userDetails.getUsername());

                // Check API user restriction
                Set<Role> roles = userByEmail.getRoles();
                boolean isApiUser = roles.stream().anyMatch(role -> role.getName() == Roles.API_USER);
                if (isApiUser) throw new BadCredentialsException("Unauthorized user.");

                // Check password reset
                if (!request.getRequestURL().toString().endsWith("/api/v1/auth/change-password")) {
                    if (Boolean.TRUE.equals(userByEmail.getHasResetPassword()) &&
                            Boolean.FALSE.equals(userByEmail.getHasChangedPassword())) {
                        throw new BadCredentialsException("Password Reset. Please login");
                    }
                }

                // Check if inactive
                if (Status.INACTIVE.equals(userByEmail.getStatus())) {
                    throw new BadCredentialsException("User is inactive.");
                }

                // Validate JWT
                if (Boolean.TRUE.equals(jwtService.validateToken(token, userDetails))) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new BadCredentialsException("Invalid Token Provided");
                }
            }

        } catch (IllegalArgumentException e) {
            logger.error("Illegal Argument while fetching the username", e);
            request.setAttribute(AppConstants.EXCEPTION, e);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired", e);
            request.setAttribute(AppConstants.EXCEPTION, e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token", e);
            request.setAttribute(AppConstants.EXCEPTION, e);
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage(), e);
            request.setAttribute(AppConstants.EXCEPTION, e);
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
            request.setAttribute(AppConstants.EXCEPTION, e);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Exclude specific endpoint(s) from JWT filter
        return request.getRequestURL().toString().endsWith("/api/v1/compress-api-user/compressFiles");
    }
}
