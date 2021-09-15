package com.a506.mirinae.domain.funding;

import lombok.Getter;

@Getter
public class FundingIdRes {
    Long funding_id;

    public FundingIdRes(Long funding_id) {
        this.funding_id = funding_id;
    }
}
