package org.example.users.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.users.dto.*;
import org.example.users.entity.User;
import org.example.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

        private  final UserRepository userRepository;

        // 저장
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(
               request.getName(),
               request.getEmail(),
               request.getAddress()
        );
        User savedUser = userRepository.save(user); // User를 save

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getAddress()

        );
    }
    // 단 건 조회
    @Transactional(readOnly = true)
    public GetOneUserResponse getOne(Long userId) {
       User user= userRepository.findById(userId).orElseThrow(
               () -> new IllegalStateException("없는 유저입니다.")
       );

       return new GetOneUserResponse(
               user.getId(),
               user.getName(),
               user.getEmail(),
               user.getAddress()
       );
    }
    // 다 건 조회
    @Transactional(readOnly = true)
    public List<GetOneUserResponse> getAll() {
        List<User> users= userRepository.findAll();

        List<GetOneUserResponse> dtos = new ArrayList<>();
        for (User user : users){
            GetOneUserResponse dto = new GetOneUserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user= userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        user.update(
                request.getName(),
                request.getEmail(),
                request.getAddress()
        );
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAddress()
        );
    }

    @Transactional
    public void delete(PathVariable userId) {
        boolean existence= userRepository.existsById(userId);

        if (!existence) {
            throw new IllegalStateException("없는 유저입니다.");
        }
        // 유저가 없는 경우 -> 삭제 가능!
        userRepository.deleteById(userId);
    }
}
