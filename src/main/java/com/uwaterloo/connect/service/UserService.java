package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final String USER_NOT_FOUND_ERROR = "User with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // used by spring security to find during authentication
        return userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_ERROR, email)));
    }

    public String signUpUser(User user) {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(user.getEmail(), user.getUsername());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            if (dbUser.getEmail().equals(user.getEmail())) { //todo throw custom exceptions
                throw new IllegalStateException("User with this email already exists");
            }
            if (dbUser.getUsername().equals(user.getUsername())) {
                throw new IllegalStateException("User with this user name already exists");
            }
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        //TODO send confirmation mail here
        return "";
    }

}
