package com.CodeCraft.hubbackend.configuration;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirebaseAuthenticationTokenFilter extends OncePerRequestFilter {

    private final static String TOKEN_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authToken = request.getHeader(TOKEN_HEADER);

        if (authToken != null) {
            try {
                FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(authToken);
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(firebaseToken.getUid(), null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new SecurityException(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
