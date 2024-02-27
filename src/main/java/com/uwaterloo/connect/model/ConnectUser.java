package com.uwaterloo.connect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "connect_user")
public class ConnectUser {//TODO: To be removed

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    public ConnectUser(Integer userId) {
        this.userId = userId;
    }

    public ConnectUser() {
        this(null);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
