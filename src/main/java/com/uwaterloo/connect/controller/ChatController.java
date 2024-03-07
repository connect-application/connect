package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Chat;
import com.uwaterloo.connect.repository.ChatRepository;
import com.uwaterloo.connect.repository.FollowRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.uwaterloo.connect.Constants.ChatEndpointURLs.*;
import static com.uwaterloo.connect.Constants.Constants.SUCCESS;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserActionAuthenticator userActionAuthenticator;

    @Autowired
    FollowRepository followRepository;

    @PostMapping(ADD_CHAT)
    public ResponseEntity<String> addChat(@RequestParam(value = "toUserId") Integer toUserId,
                                  @RequestParam(value = "chatText") String chatText) {
        Integer fromUserId = userActionAuthenticator.getLoggedUser().getId().intValue();
        if (followRepository.findFollow(toUserId, fromUserId) == null) {
            return ResponseEntity.badRequest().body("User not followed");
        }
        Chat chat = new Chat(fromUserId, toUserId, chatText);
        chatRepository.save(chat);
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PostMapping(GET_CHATS)
    public List<Chat> getChats(@RequestParam(value = "toUserId") Integer toUserId) {
        Integer fromUserId = userActionAuthenticator.getLoggedUser().getId().intValue();
        return chatRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    @PostMapping(GET_ACTIVE_CHAT_USERS)
    public List<Integer> getActiveChatUsers() {
        Integer fromUserId = userActionAuthenticator.getLoggedUser().getId().intValue();
        return chatRepository.findDistinctToUserIdsByFromUserId(fromUserId);
    }

    @PostMapping(DELETE_CHAT)
    public ResponseEntity<String> deleteChat(@RequestParam(value = "chatId") Integer chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found for id: " + chatId));
        userActionAuthenticator.checkIfAuthorized(chat.getFromUserId());
        chatRepository.delete(chat);
        return ResponseEntity.ok().body(SUCCESS);
    }
}
