package com.a506.mirinae.service;

import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.donation.RankingRes;
import com.a506.mirinae.domain.funding.MyFundingRes;
import com.a506.mirinae.domain.user.*;
import com.a506.mirinae.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.exceptions.MessageDecodingException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Web3j web3 = Web3j.build(new HttpService("http://j5a5061.p.ssafy.io:1220"));
    
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
        
        if(user.getWallet()!=null) {
				try {
					walletBalance = Double.parseDouble(Convert.fromWei(web3.ethGetBalance(user.getWallet(), DefaultBlockParameterName.LATEST).send()
							.getBalance().toString(), Unit.ETHER).toString());
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("잔고가 숫자가 아님.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new IllegalArgumentException("입출력 예외  ");
				} catch (MessageDecodingException e) {
					throw new IllegalArgumentException("포맷이 맞지 않습니다");
				}
        }

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
