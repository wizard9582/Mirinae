package com.a506.mirinae.service;

import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.donation.RankingRes;
import com.a506.mirinae.domain.funding.MyFundingRes;
import com.a506.mirinae.domain.user.*;
import com.a506.mirinae.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Double walletBalance = 0.0; // 블록체인 구현 후

        return new UserRes(user, walletBalance);
    }

    public void updateUser(UpdateReq updateReq, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));

        user.updateUser(updateReq);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));
        
        userRepository.delete(user);
    }

    @Transactional
    public List<MyFundingRes> getMyDonation(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));

        return user.getDonations().stream().map(MyFundingRes::new).collect(Collectors.toList());
    }

    @Transactional
    public List<MyFundingRes> getMyFunding(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));

        return user.getFundings().stream().map(MyFundingRes::new).collect(Collectors.toList());
    }

    public void saveWallet(WalletReq walletReq, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));

        if(!(user.getWallet()==null || "".equals(user.getWallet())))
            throw new IllegalArgumentException("이미 개설된 계좌가 존재합니다!");

        user.updateWallet(walletReq);
        userRepository.save(user);
    }
}
