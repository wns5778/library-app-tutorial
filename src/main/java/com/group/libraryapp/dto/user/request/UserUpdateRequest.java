package com.group.libraryapp.dto.user.request;

public class UserUpdateRequest {

    private int id;
    private String name;

    public UserUpdateRequest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
