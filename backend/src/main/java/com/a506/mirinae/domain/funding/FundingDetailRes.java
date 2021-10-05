package com.a506.mirinae.domain.funding;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FundingDetailRes {
    private String userNickName;
    private FundingRes fundingRes;
    private LocalDateTime createdDatetime;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private FundingState fundingState;

    public FundingDetailRes(String userNickName, FundingRes fundingRes, LocalDateTime createdDatetime,
                            LocalDateTime startDatetime, LocalDateTime endDatetime, FundingState fundingState) {
        this.userNickName = userNickName;
        this.fundingRes = fundingRes;
        this.createdDatetime = createdDatetime;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.fundingState = fundingState;
    }
}
