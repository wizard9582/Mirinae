package com.a506.mirinae.domain.donation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingRes {
    private String userThumbnail;
    private String userNickname;
    private Long amount;

    public RankingRes(Donation donation){
        this.userThumbnail = donation.getUser().getProfileImage();
        this.userNickname = donation.getUser().getNickname();
        this.amount = donation.getAmount();
    }

}
