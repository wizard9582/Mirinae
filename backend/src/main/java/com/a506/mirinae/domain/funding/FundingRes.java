package com.a506.mirinae.domain.funding;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FundingRes {
    Long fundingId;
    String title;
    String categoryName;
    Long balance;
    Long goal;
    String thumbnail;
    LocalDateTime startDatetime;
    LocalDateTime endDatetime;

    public FundingRes(FundingResInterface fundingResInterface) {
        this.fundingId = fundingResInterface.getFundingId();
        this.title = fundingResInterface.getTitle();
        this.categoryName = fundingResInterface.getCategoryName();
        this.balance = fundingResInterface.getBalance();
        this.goal = fundingResInterface.getGoal();
        this.thumbnail = fundingResInterface.getThumbnail();
        this.startDatetime = fundingResInterface.getStartDatetime();
        this.endDatetime = fundingResInterface.getEndDatetime();
    }
}
