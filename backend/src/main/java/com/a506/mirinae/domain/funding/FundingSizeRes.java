package com.a506.mirinae.domain.funding;

import lombok.Getter;

import java.util.List;

@Getter
public class FundingSizeRes {
    Long pageCount;
    List<FundingRes> fundingResList;

    public FundingSizeRes(List<FundingRes> fundingResList, Long pageCount) {
        this.fundingResList = fundingResList;
        this.pageCount = pageCount;
    }
}
