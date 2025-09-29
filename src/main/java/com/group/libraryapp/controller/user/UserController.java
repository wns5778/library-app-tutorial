package com.group.libraryapp.controller.user;
// ctrl + alt + o --> import문 최적화 단축키
// ctrl + shift + n --> 검색 후 열기
// shift + F6 --> 모든 동일값 변경

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//API 진입지정으로 설정 AND UserController 클래스를 스프링 빈으로 등록
public class UserController {


    private final UserServiceV2 userService;

    public UserController(UserServiceV2 userService) {
        this.userService = userService;
    }

    //private final List<User> users = new ArrayList<>(); //비어있는 list 생성

    @PostMapping("/user") // POST /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
        //users.add(new User(request.getName(), request.getAge()));
    }

    @GetMapping("/user") // GET / user
    public List<UserResponse> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request){
        userService.updateUser(request);
    }


    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name){
        userService.deleteUser(name);
    }

}
