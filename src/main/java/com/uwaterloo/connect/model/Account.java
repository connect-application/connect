package com.uwaterloo.connect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
    private String name;

    //TODO: Consider maintaining an inbuilt ID field
    @Id
    private String email;

    public Account(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public Account() {
        this("", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
