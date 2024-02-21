package com.uwaterloo.connect.service;


import com.uwaterloo.connect.model.Token;
import com.uwaterloo.connect.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
    @Transactional
    public Token confirmToken(String token) {
        Token dbToken = getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
        if (dbToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Token already confirmed");
        }

        LocalDateTime expiredAt = dbToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }
        setConfirmedAt(token);
        return dbToken;
    }
}