package com.a506.mirinae.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRes {
    private String jwt;
    private Long id;
    private String nickname;
    private String email;
    private Boolean isJoin;

    public LoginRes(User user, Boolean isJoin, String jwt){
        this.jwt = jwt;
        this.isJoin = isJoin;
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
