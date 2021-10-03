package com.a506.mirinae.domain.funding;

import com.a506.mirinae.domain.donation.Donation;
import lombok.Getter;

@Getter
public class MyFundingRes {
    Long fundingId;
    String title;
    String thumbnail;
    FundingState fundingState;

    public MyFundingRes(Donation donation) {
        this.fundingId = donation.getFunding().getId();
        this.title = donation.getFunding().getTitle();
        this.thumbnail = donation.getFunding().getThumbnail();
        this.fundingState = donation.getFunding().getFundingState();
    }

    public MyFundingRes(Funding funding) {
        this.fundingId = funding.getId();
        this.title = funding.getTitle();
        this.thumbnail = funding.getThumbnail();
        this.fundingState = funding.getFundingState();
    }
}
