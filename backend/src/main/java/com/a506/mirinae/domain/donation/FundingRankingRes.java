package com.a506.mirinae.domain.donation;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FundingRankingRes {
    private String userThumbnail;
    private String userNickname;
    private Long amount;

    public FundingRankingRes(Donation donation){
        this.userThumbnail = donation.getUser().getProfileImage();
        this.userNickname = donation.getUser().getNickname();
        this.amount = donation.getAmount();
    }

}
