package com.SemestralnaPraca.GamingGround.config;

import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractTokenFromCookie(request);

            if (jwt != null) {
                try {
                    String username = jwtUtil.extractUsername(jwt);

                    if (username != null && jwtUtil.validateToken(jwt, username)) {
                        User user = userRepository.findUserByEmail(username);
                        if (user != null) {
                            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    } else {
                        clearCookie(response, "jwtToken");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired or invalid");
                        return;
                    }
                } catch (io.jsonwebtoken.ExpiredJwtException ex) {
                    clearCookie(response, "jwtToken");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            clearCookie(response, "jwtToken");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token signature");
        }
    }



    private void clearCookie(HttpServletResponse response, String cookieName)
    {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies())
            {
                if (cookie.getName().equals("jwtToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
