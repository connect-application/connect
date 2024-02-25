package com.uwaterloo.connect.service;

import com.uwaterloo.connect.model.Token;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final String USER_NOT_FOUND_ERROR = "User with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // used by spring security to find during authentication
        return userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_ERROR, email)));
    }

    public String signUpUser(User user) {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(user.getEmail(), user.getUsername());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            if (dbUser.isActive()) { // user is fully registered throw exception else we add/update and send verification mail again
                if (dbUser.getEmail().equals(user.getEmail())) { //todo throw custom exceptions
                    throw new IllegalStateException("User with this email already exists");
                }
                if (dbUser.getUsername().equals(user.getUsername())) {
                    throw new IllegalStateException("User with this user name already exists");
                }
            }
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        // creating token with 15 mins expiry
        Token dbToken = new Token(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        tokenService.saveToken(dbToken);
        return token;
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email,LocalDateTime.now());
    }

}
