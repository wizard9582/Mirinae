package com.a506.mirinae.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginReq {
    private String oauthType;
    private String email;
    private String nickname;

    public User toEntity(){
        return User.builder()
                .oauthType(OauthType.valueOf(this.oauthType))
                .email(this.email)
                .nickname(this.nickname)
                .build();
    }
}
