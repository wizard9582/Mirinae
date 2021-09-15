package com.a506.mirinae.service;

import com.a506.mirinae.domain.donation.DonationRepository;
import com.a506.mirinae.domain.donation.RankingRes;
import com.a506.mirinae.domain.donation.RankingResInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("Ranking Service")
@RequiredArgsConstructor
public class RankingService {

    private final DonationRepository donationRepository;

    @Transactional
    public List<RankingRes> getFundingRanking(Long fundingId) {
        return donationRepository
                .findRankingByFundingId(fundingId, PageRequest.of(0,10, Sort.Direction.DESC, "amount"))
                .stream().map(RankingRes::new).collect(Collectors.toList());
    }

    @Transactional
    public List<RankingRes> getCategoryRanking(Long categoryId) {
        return null;
//        donationRepository
//                .findAllByFundingCategoryIdGroupByUser(categoryId, PageRequest.of(0,10,Sort.Direction.DESC, "amount"))
//                .stream().map(RankingRes::new).collect(Collectors.toList());
    }
}
