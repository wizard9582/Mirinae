package com.a506.mirinae.domain.donation;

import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.funding.Funding;
import com.a506.mirinae.domain.user.User;
import lombok.Getter;

@Getter
public class DonationReq {
    Long fundingId;
    Double amount;
    String key;

    public Donation toEntity(User user, Funding funding, String tx_id) {
        return Donation.builder()
                .user(user)
                .funding(funding)
                .amount(amount)
                .txId(tx_id)
                .build();
    }
}
