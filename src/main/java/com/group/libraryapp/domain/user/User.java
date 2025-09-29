package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

@Entity
public class User {

    @Id // 이 필드를 primary key로 간주한다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //primary key는 자동 생성되는 값이다
    private Long id = null;

    @Column(nullable = false, length = 20)//, name="name" 객체 변수명과 테이블 필드명이 동일하니 생략 가능) // == name varchar(20)
    private String name;
    
    //age는 완전 동일하니 @Column 생략 가능
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();


    protected User() {}

    public User(String name, Integer age) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void loanBook(String bookName){
        this.userLoanHistories.add(new UserLoanHistory(this, bookName, false));
    }

    public void returnBook(String bookName){
        UserLoanHistory targetHistory = this.userLoanHistories.stream().
                filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();
    }
}
