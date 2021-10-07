package com.a506.mirinae.domain.funding;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FundingDetailRes {
    private String userNickName;
    private FundingRes fundingRes;
    private String image;
    private LocalDateTime createdDatetime;
    private FundingState fundingState;
    private String content;

    public FundingDetailRes(String userNickName, FundingRes fundingRes, LocalDateTime createdDatetime,
                            FundingState fundingState, String image, String content) {
        this.userNickName = userNickName;
        this.fundingRes = fundingRes;
        this.createdDatetime = createdDatetime;
        this.fundingState = fundingState;
        this.image = image;
        this.content = content;
    }
}
