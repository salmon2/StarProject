package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.SignUpRequestDto;
import com.sparta.StarProject.dto.UserRequestDto;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    public User registerUser(SignUpRequestDto requestDto) throws StarProjectException {
        String password = passwordEncoder.encode(requestDto.getPassword());

        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (username.isEmpty()){
            throw new StarProjectException(ErrorCode.USERNAME_NOT_FOUND);
        }
        if (found.isPresent()) {
            throw new StarProjectException(ErrorCode.USERNAME_DUPLICATE);
        }

        String nickname = requestDto.getNickname();
        Optional<User> found2 = userRepository.findByNickname(nickname);
        if (nickname.isEmpty()){
            throw new StarProjectException(ErrorCode.NICKNAME_NOT_FOUND);
        }
        if (found2.isPresent()) {
            throw new StarProjectException(ErrorCode.NICKNAME_DUPLICATE);
        }

        //비밀번호 == 비밀번호 체크
        String pw = requestDto.getPassword();
        String pwCheck = requestDto.getPasswordCheck();

        if (pw.isEmpty()){
            throw new StarProjectException(ErrorCode.PASSWORD_FOT_FOUND);
        }
        //패스워드 8자 이상 20자 이하
        if (!pw.isEmpty() && !pwCheck.isEmpty()) {
            if(pw.length() >= 8 && pw.length() <= 20){
                if(!pw.equals(pwCheck)){
                    throw new StarProjectException(ErrorCode.PASSWORD_CHECK);
                }
            }
        }

        User user = new User(username, password, nickname);
        return userRepository.save(user);
    }

    //로그인
    public User login(UserRequestDto requestDto) throws StarProjectException {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new StarProjectException(ErrorCode.USER_NOT_FOUND)
        );
        String username = requestDto.getUsername();
        if (username.isEmpty()){
            throw new StarProjectException(ErrorCode.USERNAME_NOT_FOUND);
        }
        String password = requestDto.getPassword();
        if (password.isEmpty()){
            throw new StarProjectException(ErrorCode.PASSWORD_FOT_FOUND);
        }
        //패스워드 암호화
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new StarProjectException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    //username 중복
    public Map<String, String> sameId(UserRequestDto userRequestDto) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(null);

        Map<String, String> result = new HashMap<>();
        if (user == null) {
            result.put("code", "200");
            result.put("msg", "성공");
            return result;
        }
        result.put("code", "501");
        result.put("msg", "사용 불가능한 유저네임 입니다.");
        return result;
    }
    public Map<String, String> sameNickname (SignUpRequestDto signUpRequestDto){
        User user = userRepository.findByNickname(signUpRequestDto.getNickname()).orElseThrow(null);

        Map<String, String> result = new HashMap<>();
        if(user == null) {
            result.put("code", "200");
            result.put("msg", "성공");
            return result;
        }
        result.put("code" , "501");
        result.put("message", "사용 불가능한 닉네임입니다.");
        return result;
    }

}
