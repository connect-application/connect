package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.*;
import com.uwaterloo.connect.repository.ChatRepository;
import com.uwaterloo.connect.repository.FollowRepository;
import com.uwaterloo.connect.security.UserActionAuthenticator;
import org.hibernate.internal.util.MutableBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.uwaterloo.connect.Constants.Constants.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {
    @Mock
    UserActionAuthenticator userActionAuthenticator;
    @Mock
    ChatRepository chatRepository;
    @Mock
    FollowRepository followRepository;
    @InjectMocks
    ChatController chatController;

    private static final User user = new User("", "", "", "", "", null, null);
    
    private static final Chat chat = new Chat(1, 2, "Hey");
    
    static {
        user.setId(1L);
    }
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(userActionAuthenticator.getLoggedUser()).thenReturn(user);
    }

    @Test
    void addChat() {
        List<Chat> chats = new ArrayList<>();
        Mockito.when(chatRepository.save(Mockito.any(Chat.class)))
                .thenAnswer(i -> {
                    chats.add((Chat) i.getArguments()[0]);
                    return i.getArguments()[0];
                });
        Mockito.when(followRepository.findFollow(2, 1)).thenReturn(new Follow());
        ResponseEntity<String> response = chatController.addChat(2, "Hey");
        assertEquals(chats.size(), 1);
        assertEquals(response.getBody(), SUCCESS);
    }

    @Test
    void addChatFail() {
        List<Chat> chats = new ArrayList<>();
        Mockito.when(chatRepository.save(Mockito.any(Chat.class)))
                .thenAnswer(i -> {
                    chats.add((Chat) i.getArguments()[0]);
                    return i.getArguments()[0];
                });
        Mockito.when(followRepository.findFollow(2, 1)).thenReturn(new Follow());
        ResponseEntity<String> response = chatController.addChat(3, "Hey");
        assertEquals(chats.size(), 0);
        assertEquals(response.getBody(), "User not followed");
    }

    @Test
    void getChats() {
        Mockito.when(chatRepository.findByFromUserIdAndToUserId(Mockito.any(), Mockito.any()))
                .thenReturn(List.of(chat));
        List<Chat> chats = chatController.getChats(2);
        assertEquals(chats.size(), 1);
    }

    @Test
    void getActiveChatUsers() {
        Mockito.when(chatRepository.findDistinctToUserIdsByFromUserId(1)).thenReturn(List.of(2, 3));
        List<Integer> activeChatUsers = chatController.getActiveChatUsers();
        assertEquals(activeChatUsers.size(), 2);
    }

    @Test
    void deleteChat() {
        Mockito.when(chatRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(chat));
        MutableBoolean deleted = new MutableBoolean(false);
        Mockito.doAnswer(i -> {
            deleted.setValue(true);
            return i.getArguments()[0];
        }).when(chatRepository).delete(Mockito.any());
        ResponseEntity<String> response = chatController.deleteChat(1);
        assertTrue(deleted.getValue());
        assertEquals(response.getBody(), SUCCESS);
    }
}