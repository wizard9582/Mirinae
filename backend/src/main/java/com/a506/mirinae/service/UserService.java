package com.a506.mirinae.service;

import com.a506.mirinae.domain.user.*;
import com.a506.mirinae.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginRes login(LoginReq loginReq){
        Optional<User> user = userRepository.findByEmailAndOauthType(loginReq.getEmail(), OauthType.valueOf(loginReq.getOauthType()));
        Boolean isJoin = user.isPresent();

        User joinedUser;
        if(isJoin)
            joinedUser = user.get();
        else { //가입
            joinedUser = userRepository.save(loginReq.toEntity());
        }

        String jwt = jwtTokenProvider.createToken(joinedUser.getId(), joinedUser.getRoles());
        return new LoginRes(joinedUser, isJoin, jwt);
    }

    public UserRes getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));
        String walletBalance = ""; // 블록체인 구현 후

        return new UserRes(user, walletBalance);
    }

    public void updateUser(UpdateReq updateReq, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));

        user.updateUser(updateReq);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));

        userRepository.delete(user);
    }
}
