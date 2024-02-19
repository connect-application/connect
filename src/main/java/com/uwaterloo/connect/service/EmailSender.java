package com.uwaterloo.connect.service;

public interface EmailSender {
    void send(String to,String email);
}