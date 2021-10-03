package com.a506.mirinae.domain.funding;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FundingRes {
    Long fundingId;
    String title;
    String categoryName;
    Double balance;
    Double goal;
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

    public FundingRes(Funding f) {
        this.fundingId = f.getId();
        this.title = f.getTitle();
        this.categoryName = f.getCategory().getName();
        this.balance = 0.0;
        this.goal = f.getGoal();
        this.thumbnail = f.getThumbnail();
        this.startDatetime = f.getStartDatetime();
        this.endDatetime = f.getEndDatetime();
    }
}
