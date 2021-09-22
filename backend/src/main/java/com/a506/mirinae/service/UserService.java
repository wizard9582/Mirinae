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

    //TODO wallet, sign 채우기
    @Transactional
    public LoginRes login(LoginReq loginReq){
        Optional<User> user = userRepository.findByEmailAndOauthType(loginReq.getEmail(), OauthType.valueOf(loginReq.getOauthType()));
        Boolean isJoin = user.isPresent();

        User joinedUser;
        if(isJoin)
            joinedUser = user.get();
        else { //가입
            String wallet = "";
            String sign = "";
            joinedUser = loginReq.toEntity(wallet, sign);
            userRepository.save(joinedUser);
        }

        String jwt = jwtTokenProvider.createToken(joinedUser.getId(), joinedUser.getIsAdmin(), joinedUser.getNickname());
        return new LoginRes(joinedUser, isJoin, jwt);
    }

    public UserRes getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));
        String walletBalance = ""; // 블록체인 구현 후

        return new UserRes(user, walletBalance);
    }
}
