package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(UserCreateRequest request){
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
        //findAll() -> 주어지는 객체가 매핑된 테이블의 모든 데이터를 가져온다 ex. select * from user
        return userRepository.findAll().stream()
                .map(UserResponse::new)//.map(user -> new UserResponse(user.getId(), user.getName(), user.getAge())
                .collect(Collectors.toList());

    }

    @Transactional
    public void updateUser(UserUpdateRequest request){
        //findById: id를 기준으로 매핑된 테이블의 모든 데이터를 가져온다
        User user = userRepository.findById((long) request.getId())
                .orElseThrow(IllegalArgumentException::new);

        //save: 주어지는 객체를 저장하거나 업데이트 시켜준다.
        user.updateName(request.getName());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String name){
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }

}
