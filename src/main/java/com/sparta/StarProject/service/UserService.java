package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Like;
import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.repository.LikeRepository;
import com.sparta.StarProject.repository.boardRepository.BoardRepository;
import com.sparta.StarProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;


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


    public Map<String, String> sameUsername(String username) throws StarProjectException {
        Optional<User> user = userRepository.findByUsername(username);

        Map<String, String> result = new HashMap<>();
        if (!user.isPresent()) {
            result.put("code", "200");
            result.put("msg", "성공");
            result.put("data", null);
            return result;
        }
        else{
            throw new StarProjectException(ErrorCode.USERNAME_DUPLICATE);
        }
    }



    public Map<String, String> sameNickname (String nickname) throws StarProjectException {
        Optional<User> user = userRepository.findByNickname(nickname);
        Map<String, String> result = new HashMap<>();
        if (!user.isPresent()) {
            result.put("code", "200");
            result.put("msg", "성공");
            result.put("data", null);
            return result;
        }
        else{
            throw new StarProjectException(ErrorCode.NICKNAME_DUPLICATE);
        }
    }


    public void myLeave(User user) {
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException(ErrorCode.USER_NOT_FOUND.getMessage())
        );

        List<Board> boardList = findUser.getBoardList();

        deletUser(findUser);

        for (Board board : boardList) {
            board.setLikeCount(Long.valueOf(likeRepository.countByBoard(board)));
        }

    }

    @Transactional
    public void deletUser(User findUser) {
        userRepository.deleteById(findUser.getId());
    }

    @Transactional
    public void myUpdate(User user, UserUpdateDto userUpdateDto) throws StarProjectException {
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new NullPointerException(ErrorCode.USER_NOT_FOUND.getMessage())
        );

        if(!userUpdateDto.getPassword().equals(userUpdateDto.getPasswordCheck())){
            throw new StarProjectException(ErrorCode.PASSWORD_CHECK);
        }

        findUser.updateUser(
                userUpdateDto.getNickname(),
                passwordEncoder.encode(userUpdateDto.getPassword())
        );

    }

    public Page<MyBoardDto> getMyBoardDtoList(User user, int offset) {
        PageRequest pageRequest = PageRequest.of(offset, 4);
        Page<MyBoardDto> result = boardRepository.findAllByUserCustom(user, pageRequest);

        return result;
    }


}
