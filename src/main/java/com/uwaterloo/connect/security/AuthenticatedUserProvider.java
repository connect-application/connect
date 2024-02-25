package com.uwaterloo.connect.security;

import com.uwaterloo.connect.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {
    // Use this method to get authenticated user anywhere in the code
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return (User) authentication.getPrincipal();
        } else {
            throw new IllegalStateException("No authenticated user found");
        }
    }
}
