package com.uwaterloo.connect.service;

import com.uwaterloo.connect.dto.ChangePasswordRequest;
import com.uwaterloo.connect.dto.ResetPasswordRequest;
import com.uwaterloo.connect.enums.UserRole;
import com.uwaterloo.connect.model.Token;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenService tokenService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private UserService userService;
    @InjectMocks
    LoginService loginService;


    private static final User dbUser = new User("John", "Doe", "johndoe", "john@example.com", "password123", LocalDate.of(1990, 5, 15), UserRole.USER);
    private static final Token dbToken = new Token("token123", now(), null, dbUser);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(userService.loadUserByUsername(Mockito.any()))
                .thenReturn(dbUser);
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> {
                    return i.getArguments()[0];
                });
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.any()))
                .thenAnswer(i -> "encoded-password");
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void login() {
    }

    @Test
    void resetPassword() {
        dbUser.setPassword("password123");
        Mockito.when(tokenService.getToken(Mockito.any()))
                .thenReturn(Optional.of(dbToken));
        loginService.resetPassword(new ResetPasswordRequest("john@example.com", "password", "token123"));
        assertNotEquals("password123", dbUser.getPassword());
    }

    @Test
    void changePassword() {
        dbUser.setPassword("password123");
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("john@example.com", "password123", "password");
        loginService.changePassword(changePasswordRequest);
        assertNotEquals("password123", dbUser.getPassword());

    }
}