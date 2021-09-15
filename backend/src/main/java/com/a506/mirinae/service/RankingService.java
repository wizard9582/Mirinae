package com.a506.mirinae.service;

import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.donation.DonationRepository;
import com.a506.mirinae.domain.donation.RankingRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("Ranking Service")
@RequiredArgsConstructor
public class RankingService {

    private final DonationRepository donationRepository;

    @Transactional
    public List<RankingRes> getFundingRanking(Long funding_id) {
        Page<Donation> donations = donationRepository
                .findAllByFundingId(funding_id, PageRequest.of(0,10, Sort.Direction.ASC, "amount"));
        List<RankingRes> rankingResList = new ArrayList<>();
        for(Donation donation : donations)
            rankingResList.add(new RankingRes(donation));
        return rankingResList;
    }

}
