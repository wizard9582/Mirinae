package com.a506.mirinae.domain.user;

import lombok.Getter;

@Getter
public class UserRes {
    private String email;
    private String image;
    private String nickname;
    private String walletAddress;
    private Double walletBalance;

    public UserRes(User user, Double walletBalance) {
        this.email = user.getEmail();
        this.image = user.getProfileImage();
        this.nickname = user.getNickname();
        this.walletAddress = user.getWallet();
        this.walletBalance = walletBalance;
    }
}
