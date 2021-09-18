package com.a506.mirinae.domain.funding;

import lombok.Getter;

@Getter
public class FundingRes {
    Long fundingId;
    String title;
    String categoryName;
    Long balance;
    Long goal;
    String thumbnail;

    public FundingRes(FundingResInterface fundingResInterface) {
        this.fundingId = fundingResInterface.getId();
        this.title = fundingResInterface.getTitle();
        this.categoryName = fundingResInterface.getCategoryName();
        this.balance = fundingResInterface.getBalance();
        this.goal = fundingResInterface.getGoal();
        this.thumbnail = fundingResInterface.getThumbnail();
    }
}
