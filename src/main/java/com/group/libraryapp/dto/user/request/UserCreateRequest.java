package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {

    private String name;
    private Integer age; //Integer 변수는 null 표현가능!

    public UserCreateRequest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
