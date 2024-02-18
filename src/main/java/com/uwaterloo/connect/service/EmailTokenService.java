package com.uwaterloo.connect.service;


import com.uwaterloo.connect.model.EmailToken;
import com.uwaterloo.connect.repository.EmailTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailTokenService {

    private final EmailTokenRepository emailTokenRepository;

    public void saveEmailToken(EmailToken token) {
        emailTokenRepository.save(token);
    }

    public Optional<EmailToken> getToken(String token) {
        return emailTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return emailTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}