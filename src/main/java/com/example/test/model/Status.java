package com.example.test.model;

public enum Status {
    NEW("NEW"),
    ACTIVE("ACTIVE"),
    DEACTIVATED("DEACTIVATED");

    private String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
