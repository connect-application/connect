package com.uwaterloo.connect.Constants;

public enum EnumCategory {
    VALUE1("Fitness"),
    VALUE2("Education"),
    VALUE3("Professional Goals"),
    VALUE4("Daily Goal");

    private final String categoryName;

    // Constructor
    EnumCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getter method for description
    public String getCategoryName() {
        return categoryName;
    }
}
