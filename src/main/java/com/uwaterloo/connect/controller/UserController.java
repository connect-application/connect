package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.dto.UserResponse;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.UserRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import com.uwaterloo.connect.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

import static com.uwaterloo.connect.Constants.Constants.SUCCESS;
import static com.uwaterloo.connect.Constants.UserEndpointURLs.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @Autowired
    UserService userService;

    public ResponseEntity<UserResponse> returnFromController(UserResponse userResponse) {
        if (userResponse != null) {
            return ResponseEntity.ok().body(userResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(RETRIEVE_USER)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return returnFromController(userResponse);
    }

    @PostMapping(EDIT_FIRST_NAME)
    public ResponseEntity<String> editFirstName(@RequestParam(value = "firstName") String firstName) {
        User loggedUser = userActionAuthenticator.getLoggedUser();
        loggedUser.setFirstName(firstName);
        userRepository.save(loggedUser);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(EDIT_LAST_NAME)
    public ResponseEntity<String> editLastName(@RequestParam(value = "lastName") String lastName) {
        User loggedUser = userActionAuthenticator.getLoggedUser();
        loggedUser.setLastName(lastName);
        userRepository.save(loggedUser);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(EDIT_ABOUT)
    public ResponseEntity<String> editAbout(@RequestParam(value = "about") String about) {
        User loggedUser = userActionAuthenticator.getLoggedUser();
        loggedUser.setAbout(about);
        userRepository.save(loggedUser);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(EDIT_DOB)
    public ResponseEntity<String> editLastName(@RequestParam(value = "dateOfBirth") LocalDate dateOfBirth) {
        User loggedUser = userActionAuthenticator.getLoggedUser();
        loggedUser.setDateOfBirth(dateOfBirth);
        userRepository.save(loggedUser);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(EDIT_PROFILE_PIC)
    public ResponseEntity<String> editProfilePic(@RequestParam("profilePic") MultipartFile profilePic) {
        User loggedUser = userActionAuthenticator.getLoggedUser();
        try {
            loggedUser.setProfilePic(profilePic.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.toString());
        }
        userRepository.save(loggedUser);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @GetMapping(RETRIEVE_CURRENT_USER)
    public User retrieveCurrentUser() {
        return userActionAuthenticator.getLoggedUser();
    }
}
