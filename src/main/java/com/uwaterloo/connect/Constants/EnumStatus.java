package com.uwaterloo.connect.Constants;

public enum EnumStatus {
    VALUE1("Created"),
    VALUE2("In Progress"),
    VALUE3("Paused"),
    VALUE4("Finished");

    private final String statusName;

    // Constructor
    EnumStatus(String statusName) {
        this.statusName = statusName;
    }

    // Getter method for description
    public String getStatusName() {
        return statusName;
    }
}
