//package com.sparta.StarProject.service;
//
//import com.sparta.StarProject.User;
//import com.sparta.StarProject.domain.repository.UserRepository;
//import com.sparta.StarProject.dto.SignUpRequestDto;
//import com.sparta.StarProject.dto.UserRequestDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    //회원가입
//    public User registerUser(SignUpRequestDto requestDto) {
//        String password = passwordEncoder.encode(requestDto.getPassword());
//
//        String username = requestDto.getUsername();
//        Optional<User> found = userRepository.findByUsername(username);
//
//        String nickname = requestDto.getNickname();
//        Optional<User> found2 = userRepository.findByNickname(nickname);
//
////        String password = requestDto.getPassword();
////        String passwordCheck = requestDto.getPassword();
//
//        User user = new User(username, password, nickname);
//        return userRepository.save(user);
//    }
//    //로그인
//    public User login(UserRequestDto requestDto){
//        User user = userRepository.findByUsername(requestDto.getUsername());
//
//        return user;
//    }
//
//}
