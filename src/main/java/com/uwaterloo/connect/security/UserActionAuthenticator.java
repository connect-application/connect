package com.uwaterloo.connect.security;

import com.uwaterloo.connect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserActionAuthenticator {

    @Autowired
    AuthenticatedUserProvider authenticatedUserProvider;
    public void checkIfAuthorized(Integer userId) {
        if (authenticatedUserProvider.getCurrentUser().getId().intValue() != userId) {
            throw new RuntimeException("Unauthorized operation");
        }
    }

    public User getLoggedUser() {
        User user = authenticatedUserProvider.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("No logged user found");
        }
        return user;
    }
}
