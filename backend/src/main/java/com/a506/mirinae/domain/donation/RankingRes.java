package com.a506.mirinae.domain.donation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingRes {
    private Long userId;
    private String userProfileImage;
    private String userNickname;
    private Long amount;

    public RankingRes(RankingResInterface rankingResInterface){
        this.userId = rankingResInterface.getUserId();
        this.userProfileImage = rankingResInterface.getUserProfileImage();
        this.userNickname = rankingResInterface.getUserNickname();
        this.amount = rankingResInterface.getAmount();
    }

}
