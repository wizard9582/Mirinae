package com.a506.mirinae.domain.funding;

import lombok.Getter;

@Getter
public class FundingIdRes {
    Long fundingId;

    public FundingIdRes(Long fundingId) {
        this.fundingId = fundingId;
    }
}
